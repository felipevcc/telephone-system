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
    @copyright: TechCamp
    *******************************************************************************/
    PROCEDURE Proc_UpdateCustomer (Ip_customer_data IN TYP_CUSTOMER_UPDATE) IS
    BEGIN
        SELECT NULL
        INTO NULL
        FROM CUSTOMER
        WHERE CUSTOMER_ID = Ip_customer_data.CUSTOMER_ID
        FOR UPDATE;

        UPDATE CUSTOMER
        SET
            ADDRESS = Ip_customer_data.ADDRESS,
            EMAIL = Ip_customer_data.EMAIL,
            PHONE_NUMBER = Ip_customer_data.PHONE_NUMBER
        WHERE CUSTOMER_ID = Ip_customer_data.CUSTOMER_ID;
        
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
