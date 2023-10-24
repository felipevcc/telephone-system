/*******************************************************************************
Description: Customer management package
Author: Andres Felipe Villamizar Collazos
Date 17-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE APP_ASIG_NUM_TEL.PCK_CUSTOMER IS

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
    );

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
    );

END PCK_CUSTOMER;
