/*******************************************************************************
Description: Telephone number management package
Author: Andres Felipe Villamizar Collazos
Date 16-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE APP_ASIG_NUM_TEL.PCK_TELEPHONE_NUMBER IS

    /*******************************************************************************
    Description: Secondary procedure of Proc_TrackingNumbers to process each transaction
    Author: Andres Felipe Villamizar Collazos
    Date 16-10-2023
    *******************************************************************************/
    PROCEDURE Proc_ProcessTransaction (
        Ip_center_id IN NUMBER,
        Ip_customer_id IN NUMBER,
        Ip_phone_number IN VARCHAR2,
        Ip_assignment_date IN DATE,
        Ip_release_date IN DATE,
        Op_error_occurred OUT BOOLEAN
    );

    /*******************************************************************************
    Description: Automatic and manual procedure for tracking telephone numbers for complete release
    Author: Andres Felipe Villamizar Collazos
    Date 16-10-2023
    *******************************************************************************/
    PROCEDURE Proc_TrackingNumbers;

    /*******************************************************************************
    Description: Procedure for assigning telephone numbers
    Author: Andres Felipe Villamizar Collazos
    Date 23-10-2023
    *******************************************************************************/
    PROCEDURE Proc_AssignTelephoneNumber (
        Ip_center_id IN NUMBER,
        Ip_customer_id IN NUMBER,
        Ip_phone_number IN VARCHAR2
    );

    /*******************************************************************************
    Description: Procedure for releasing telephone numbers
    Author: Andres Felipe Villamizar Collazos
    Date 16-10-2023
    *******************************************************************************/
    PROCEDURE Proc_ReleaseTelephoneNumber (Ip_number IN NUMBER);

END PCK_TELEPHONE_NUMBER;
