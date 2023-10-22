/*******************************************************************************
Description: Customer management package
Author: Andres Felipe Villamizar Collazos
Date 17-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE BODY APP_ASIG_NUM_TEL.PCK_CUSTOMER IS

    /*******************************************************************************
    Description: Procedure to update a customer
    Author: Andres Felipe Villamizar Collazos
    Date 17-10-2023
    *******************************************************************************/
    PROCEDURE Proc_UpdateCustomer (
        Ip_customer_id IN NUMBER,
        Ip_address IN VARCHAR2,
        Ip_email IN VARCHAR2,
        Ip_phone_number IN VARCHAR2
    ) IS
        l_customer_id CUSTOMER.CUSTOMER_ID%TYPE;
    BEGIN
        SELECT CUSTOMER_ID
        INTO l_customer_id
        FROM CUSTOMER
        WHERE CUSTOMER_ID = Ip_customer_id
        FOR UPDATE;

        UPDATE CUSTOMER
        SET
            ADDRESS = Ip_address,
            EMAIL = Ip_email,
            PHONE_NUMBER = Ip_phone_number
        WHERE CUSTOMER_ID = l_customer_id;
        
        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20001, 'The customer does not exist');
            ROLLBACK;
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Error executing the Proc_UpdateCustomer procedure ' || SQLERRM);
            ROLLBACK;
    END Proc_UpdateCustomer;

END PCK_CUSTOMER;
