/*******************************************************************************
Description: Customer management package
Author: Andres Felipe Villamizar Collazos
Date 17-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE APP_ASIG_NUM_TEL.PCK_CUSTOMER IS

    /*******************************************************************************
    Description: Procedure to update a customer
    Author: Andres Felipe Villamizar Collazos
    Date 17-10-2023
    *******************************************************************************/
    PROCEDURE Proc_UpdateCustomer (Ip_customer_data IN TYP_CUSTOMER_UPDATE);

END PCK_CUSTOMER;
