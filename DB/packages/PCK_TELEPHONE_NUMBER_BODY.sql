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
        Ip_center_id IN NUMBER,
        Ip_customer_id IN NUMBER,
        Ip_phone_number IN NUMBER,
        Ip_assignment_date IN DATE,
        Ip_release_date IN DATE,
        Op_error_occurred OUT BOOLEAN
    ) IS
        PRAGMA AUTONOMOUS_TRANSACTION;
    BEGIN
        Op_error_occurred := FALSE;

        -- Insert record into audit table
        INSERT INTO TELEPHONE_NUMBER_AUDIT (NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE)
        VALUES (SEQ_TELEPHONE_NUMBER_AUDIT.NEXTVAL, Ip_center_id, Ip_customer_id, Ip_phone_number, Ip_assignment_date, Ip_release_date);

        -- Delete record in assignment table
        DELETE FROM TELEPHONE_NUMBER
        WHERE PHONE_NUMBER = Ip_phone_number;

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
    PROCEDURE Proc_TrackingNumbers (Ip_Manual IN NUMBER DEFAULT NULL) IS
        l_time_value MINIMUM_TIME_SETTING.TIME_VALUE%TYPE;
        CURSOR c_telephone_numbers IS
            SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE
            FROM TELEPHONE_NUMBER
            WHERE RELEASE_DATE IS NOT NULL AND (SYSDATE - RELEASE_DATE) >= l_time_value;
        l_vc_telephone_numbers c_telephone_numbers%ROWTYPE;
        l_successful_count NUMBER := 0; -- Successful transaction counter
        l_total_records NUMBER := 0; -- Total number of records
        l_error_occurred BOOLEAN; -- State variable of the result of each autonomous transaction
    BEGIN
        -- Obtain the numerical value of days
        SELECT TIME_VALUE INTO l_time_value
        FROM MINIMUM_TIME_SETTING
        WHERE CREATED_AT = (SELECT MAX(CREATED_AT) FROM MINIMUM_TIME_SETTING);

        OPEN c_telephone_numbers;
        LOOP
            FETCH c_telephone_numbers INTO l_vc_telephone_numbers;
            EXIT WHEN c_telephone_numbers%NOTFOUND;

            /*l_error_occurred BOOLEAN := FALSE;*/

            Proc_ProcessTransaction(
                l_vc_telephone_numbers.CENTER_ID,
                l_vc_telephone_numbers.CUSTOMER_ID,
                l_vc_telephone_numbers.PHONE_NUMBER,
                l_vc_telephone_numbers.ASSIGNMENT_DATE,
                l_vc_telephone_numbers.RELEASE_DATE,
                l_error_occurred
            );
            IF NOT l_error_occurred THEN
                l_successful_count := l_successful_count + 1;
            END IF;
            l_total_records := l_total_records + 1;
        END LOOP;

        CLOSE c_telephone_numbers;

        DBMS_OUTPUT.PUT_LINE('Successful transactions: ' || l_successful_count);
        DBMS_OUTPUT.PUT_LINE('Total transactions: ' || l_successful_count);

    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001, 'Error executing the Proc_TrackingNumbers procedure ' || SQLERRM);
    END Proc_TrackingNumbers;

    /*******************************************************************************
    Description: Procedure for releasing telephone numbers
    Author: Andres Felipe Villamizar Collazos
    Date 16-10-2023
    *******************************************************************************/
    PROCEDURE Proc_ReleaseTelephoneNumber (Ip_Number IN NUMBER) IS
        l_release_date TELEPHONE_NUMBER.RELEASE_DATE%TYPE;
        e_already_released EXCEPTION;
    BEGIN
        SELECT RELEASE_DATE
        INTO l_release_date
        FROM TELEPHONE_NUMBER
        WHERE PHONE_NUMBER = Ip_Number
        FOR UPDATE;

        IF l_release_date IS NULL THEN
            UPDATE TELEPHONE_NUMBER
            SET RELEASE_DATE = SYSDATE
            WHERE PHONE_NUMBER = Ip_Number;
        ELSE
            RAISE e_already_released;
        END IF;
        
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
