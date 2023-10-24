/*******************************************************************************
Description: Customer management package
Author: Andres Felipe Villamizar Collazos
Date 17-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE BODY APP_ASIG_NUM_TEL.PCK_CUSTOMER IS

    /*******************************************************************************
    Description: Procedure to create a customer
    Author: Andres Felipe Villamizar Collazos
    Date 23-10-2023
    *******************************************************************************/
    PROCEDURE Proc_CreateCustomer (
        Ip_customer_type_id IN NUMBER,
        Ip_name IN VARCHAR2,
        Ip_last_name IN VARCHAR2,
        Ip_birthdate IN DATE,
        Ip_document_type_id IN NUMBER,
        Ip_document IN VARCHAR2,
        Ip_address IN VARCHAR2,
        Ip_area_id IN NUMBER,
        Ip_email IN VARCHAR2,
        Ip_phone_number IN VARCHAR2,
        Op_customer_id OUT NUMBER
    ) IS
    BEGIN
        Op_customer_id := SEQ_CENTER.NEXTVAL;

        INSERT INTO CENTER (CUSTOMER_ID, CUSTOMER_TYPE_ID, NAME, LAST_NAME, BIRTHDATE, DOCUMENT_TYPE_ID, DOCUMENT, ADDRESS, AREA_ID, EMAIL, PHONE_NUMBER)
        VALUES (Op_customer_id, Ip_customer_type_id, Ip_name, Ip_last_name, Ip_birthdate, Ip_document_type_id, Ip_document, Ip_address, Ip_area_id, Ip_email, Ip_phone_number);

        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            Op_customer_id := NULL;
            RAISE_APPLICATION_ERROR(-20001, 'Error executing the Proc_CreateCustomer procedure ' || SQLERRM);
            ROLLBACK;
    END Proc_CreateCustomer;

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
