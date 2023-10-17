/*******************************************************************************
Description: Customer management package
Author: Andres Felipe Villamizar Collazos
Date 17-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE APP_ASIG_NUM_TEL.PCK_CUSTOMER IS

    /*******************************************************************************
    Description: Secondary procedure of Proc_TrackingNumbers to process each transaction
    Author: Andres Felipe Villamizar Collazos
    Date 17-10-2023
    @copyright: TechCamp
    *******************************************************************************/
    PROCEDURE Proc_TransactionProcessing (
        Ip_center_id IN NUMBER,
        Ip_customer_id IN NUMBER,
        Ip_phone_number IN NUMBER,
        Ip_assignment_date IN DATE,
        Ip_release_date IN DATE,
        Op_error_occurred OUT BOOLEAN
    );

    /*******************************************************************************
    Description: Automatic and manual procedure for tracking telephone numbers for complete release
    Author: Andres Felipe Villamizar Collazos
    Date 17-10-2023
    @copyright: TechCamp
    *******************************************************************************/
    PROCEDURE Proc_TrackingNumbers (Ip_Manual IN NUMBER DEFAULT NULL);

    /*******************************************************************************
    Description: Procedure for releasing telephone numbers
    Author: Andres Felipe Villamizar Collazos
    Date 17-10-2023
    @copyright: TechCamp
    *******************************************************************************/
    PROCEDURE Proc_ReleaseTelephoneNumber (Ip_Number IN NUMBER);

END PCK_CUSTOMER;
