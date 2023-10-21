/*******************************************************************************
Description: Center management package
Author: Andres Felipe Villamizar Collazos
Date 17-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE BODY APP_ASIG_NUM_TEL.PCK_CENTER IS

    /*******************************************************************************
    Description: Procedure to create a center checking if the new range of telephone numbers is valid
    Author: Andres Felipe Villamizar Collazos
    Date 17-10-2023
    *******************************************************************************/
    PROCEDURE Proc_CreateCenter (
        Ip_name IN VARCHAR2,
        Ip_address IN VARCHAR2,
        Ip_email IN VARCHAR2,
        Ip_phone_number IN VARCHAR2,
        Ip_initial_number IN NUMBER,
        Ip_final_number IN NUMBER,
        Op_center_id OUT NUMBER
    ) IS
        e_invalid_range EXCEPTION;
        e_range_not_available EXCEPTION;
    BEGIN
        IF Ip_initial_number >= Ip_final_number THEN
            RAISE e_invalid_range;
        END IF;
        
        FOR range IN (SELECT INITIAL_NUMBER, FINAL_NUMBER FROM CENTER FOR UPDATE) LOOP
            IF (Ip_initial_number >= range.INITIAL_NUMBER AND Ip_final_number <= range.FINAL_NUMBER) OR
                (Ip_initial_number <= range.INITIAL_NUMBER AND Ip_final_number >= range.INITIAL_NUMBER) OR
                (Ip_initial_number <= range.FINAL_NUMBER AND Ip_final_number >= range.FINAL_NUMBER)
            THEN
                RAISE e_range_not_available;
            END IF;
        END LOOP;

        Op_center_id := SEQ_CENTER.NEXTVAL;

        INSERT INTO CENTER (CENTER_ID, NAME, ADDRESS, EMAIL, PHONE_NUMBER, INITIAL_NUMBER, FINAL_NUMBER)
        VALUES (Op_center_id, Ip_name, Ip_address, Ip_email, Ip_phone_number, Ip_initial_number, Ip_final_number);

        COMMIT;
    
    EXCEPTION
        WHEN e_invalid_range THEN
            RAISE_APPLICATION_ERROR(-20001, 'The initial number must be less than the final number');
            ROLLBACK;
        WHEN e_range_not_available THEN
            RAISE_APPLICATION_ERROR(-20002, 'Range not available. New range overlaps with an existing range');
            ROLLBACK;
        WHEN OTHERS THEN
            Op_center_id := NULL;
            RAISE_APPLICATION_ERROR(-20003, 'Error executing the Proc_CreateCenter procedure ' || SQLERRM);
            ROLLBACK;
    END Proc_CreateCenter;

    /*******************************************************************************
    Description: Procedure to update a center
    Author: Andres Felipe Villamizar Collazos
    Date 17-10-2023
    *******************************************************************************/
    PROCEDURE Proc_UpdateCenter (Ip_center_data IN TYP_CENTER_UPDATE) IS
        l_center_id CENTER.CENTER_ID%TYPE;
    BEGIN
        SELECT CENTER_ID
        INTO l_center_id
        FROM CENTER
        WHERE CENTER_ID = Ip_center_data.CENTER_ID
        FOR UPDATE;

        UPDATE CENTER
        SET
            ADDRESS = Ip_center_data.ADDRESS,
            EMAIL = Ip_center_data.EMAIL,
            PHONE_NUMBER = Ip_center_data.PHONE_NUMBER
        WHERE CENTER_ID = l_center_id;
        
        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20001, 'The center does not exist');
            ROLLBACK;
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Error executing the Proc_UpdateCenter procedure ' || SQLERRM);
            ROLLBACK;
    END Proc_UpdateCenter;

END PCK_CENTER;
