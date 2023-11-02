/*******************************************************************************
Description: Telephone number management package body
Author: Andres Felipe Villamizar Collazos
Date 16-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE BODY APP_ASIG_NUM_TEL.PCK_TELEPHONE_NUMBER IS

    /*******************************************************************************
    Description: Secondary procedure of Proc_TrackingNumbers to process each transaction
    Author: Andres Felipe Villamizar Collazos
    Date 16-10-2023
    *******************************************************************************/
    PROCEDURE Proc_ProcessTransaction (
        Ip_number_record_id IN NUMBER,
        Op_error_occurred OUT BOOLEAN
    ) IS
        PRAGMA AUTONOMOUS_TRANSACTION;
    BEGIN
        Op_error_occurred := FALSE;

        -- Update record to release telephone number completely
        UPDATE TELEPHONE_NUMBER_AUDIT
        SET IS_ACTIVE = 0
        WHERE NUMBER_RECORD_ID = Ip_number_record_id;

        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            Op_error_occurred := TRUE;
            ROLLBACK;
    END Proc_ProcessTransaction;

    /*******************************************************************************
    Description: Automatic and manual procedure for tracking telephone numbers for complete release
    Author: Andres Felipe Villamizar Collazos
    Date 16-10-2023
    *******************************************************************************/
    PROCEDURE Proc_TrackingNumbers IS
        l_time_value MINIMUM_TIME_SETTING.TIME_VALUE%TYPE;

        TYPE l_tb_ids IS TABLE OF NUMBER INDEX BY PLS_INTEGER;
        l_number_record_ids l_tb_ids;

        l_successful_count NUMBER := 0; -- Successful transaction counter
        l_total_records NUMBER := 0; -- Total number of records
        l_error_occurred BOOLEAN; -- State variable of the result of each autonomous transaction
    BEGIN
        -- Obtain the numerical value of days
        SELECT TIME_VALUE INTO l_time_value
        FROM MINIMUM_TIME_SETTING
        WHERE CREATED_AT = (SELECT MAX(CREATED_AT) FROM MINIMUM_TIME_SETTING);

        SELECT NUMBER_RECORD_ID
        BULK COLLECT INTO l_number_record_ids
        FROM TELEPHONE_NUMBER_AUDIT
        WHERE IS_ACTIVE = 1 AND (SYSDATE - RELEASE_DATE) >= l_time_value;

        l_total_records := l_number_record_ids.COUNT;

        FOR i IN 1..l_number_record_ids.COUNT LOOP
            /*l_error_occurred BOOLEAN := FALSE;*/

            Proc_ProcessTransaction(
                l_number_record_ids(i),
                l_error_occurred
            );
            IF NOT l_error_occurred THEN
                l_successful_count := l_successful_count + 1;
            END IF;
        END LOOP;

        DBMS_OUTPUT.PUT_LINE('Successful transactions: ' || l_successful_count);
        DBMS_OUTPUT.PUT_LINE('Total transactions: ' || l_total_records);

    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001, 'Error executing the Proc_TrackingNumbers procedure ' || SQLERRM);
    END Proc_TrackingNumbers;

    /*******************************************************************************
    Description: Procedure for assigning telephone numbers
    Author: Andres Felipe Villamizar Collazos
    Date 23-10-2023
    *******************************************************************************/
    PROCEDURE Proc_AssignTelephoneNumber (
        Ip_center_id IN NUMBER,
        Ip_customer_id IN NUMBER,
        Ip_phone_number IN NUMBER,
        Op_number_record_id OUT NUMBER
    ) IS
    BEGIN
        Op_number_record_id := SEQ_TELEPHONE_NUMBER.NEXTVAL;

        INSERT INTO TELEPHONE_NUMBER (NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER)
        VALUES (Op_number_record_id, Ip_center_id, Ip_customer_id, Ip_phone_number);

        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            Op_number_record_id := NULL;
            RAISE_APPLICATION_ERROR(-20001, 'Error executing the Proc_AssignTelephoneNumber procedure ' || SQLERRM);
            ROLLBACK;
    END Proc_AssignTelephoneNumber;

    /*******************************************************************************
    Description: Procedure for releasing telephone numbers
    Author: Andres Felipe Villamizar Collazos
    Date 16-10-2023
    *******************************************************************************/
    PROCEDURE Proc_ReleaseTelephoneNumber (Ip_phone_number IN NUMBER) IS
        l_vr_telephone_number TELEPHONE_NUMBER%ROWTYPE;
        l_found_number_audit NUMBER;
        e_already_released EXCEPTION;
    BEGIN
        SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE
        INTO l_vr_telephone_number
        FROM TELEPHONE_NUMBER
        WHERE PHONE_NUMBER = Ip_phone_number
        FOR UPDATE;

        SELECT COUNT(*) AS FOUND_NUMBER_AUDIT
        INTO l_found_number_audit
        FROM TELEPHONE_NUMBER_AUDIT
        WHERE PHONE_NUMBER = Ip_phone_number
        AND IS_ACTIVE = 1;

        IF l_found_number_audit > 0 THEN
            RAISE e_already_released;
        END IF;

        -- Insert record into audit table
        INSERT INTO TELEPHONE_NUMBER_AUDIT (NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE)
        VALUES (SEQ_TELEPHONE_NUMBER_AUDIT.NEXTVAL, l_vr_telephone_number.CENTER_ID, l_vr_telephone_number.CUSTOMER_ID, l_vr_telephone_number.PHONE_NUMBER, l_vr_telephone_number.ASSIGNMENT_DATE);

        -- Delete record in assignment table
        DELETE FROM TELEPHONE_NUMBER
        WHERE PHONE_NUMBER = Ip_phone_number;
        
        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20001, 'The telephone number does not exist or has not been assigned');
            ROLLBACK;
        WHEN e_already_released THEN
            RAISE_APPLICATION_ERROR(-20002, 'The telephone number has already been released');
            ROLLBACK;
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20003, 'Error executing the Proc_ReleaseTelephoneNumber procedure ' || SQLERRM);
            ROLLBACK;
    END Proc_ReleaseTelephoneNumber;

END PCK_TELEPHONE_NUMBER;
