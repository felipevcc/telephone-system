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
    @copyright: TechCamp
    *******************Cu************************************************************/
    PROCEDURE Proc_CreateCenter (
        Ip_center IN TYP_CENTER_CREATION,
        Op_center_id OUT NUMBER
    ) IS
        e_invalid_range EXCEPTION;
        e_range_not_available EXCEPTION;
    BEGIN
        IF Ip_center.INITIAL_NUMBER >= Ip_center.FINAL_NUMBER THEN
            RAISE e_invalid_range;
        END IF;
        
        FOR range IN (SELECT INITIAL_NUMBER, FINAL_NUMBER FROM CENTER FOR UPDATE) LOOP
            IF (Ip_center.INITIAL_NUMBER >= range.INITIAL_NUMBER AND Ip_center.FINAL_NUMBER <= range.FINAL_NUMBER) OR
                (Ip_center.INITIAL_NUMBER <= range.INITIAL_NUMBER AND Ip_center.FINAL_NUMBER >= range.INITIAL_NUMBER) OR
                (Ip_center.INITIAL_NUMBER <= range.FINAL_NUMBER AND Ip_center.FINAL_NUMBER >= range.FINAL_NUMBER)
            THEN
                RAISE e_range_not_available;
            END IF;
        END LOOP;

        Op_center_id := SEQ_CENTER.NEXTVAL;

        INSERT INTO CENTER (CENTER_ID, NAME, ADDRESS, EMAIL, PHONE_NUMBER, INITIAL_NUMBER, FINAL_NUMBER)
        VALUES (Op_center_id, Ip_center.NAME, Ip_center.ADDRESS, Ip_center.EMAIL, Ip_center.PHONE_NUMBER, Ip_center.INITIAL_NUMBER, Ip_center.FINAL_NUMBER);

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
    @copyright: TechCamp
    *******************************************************************************/
    PROCEDURE Proc_UpdateCenter (Ip_center IN TYP_CENTER_UPDATE);

END PCK_CENTER;
