/*******************************************************************************
Description: Center management package
Author: Andres Felipe Villamizar Collazos
Date 17-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE APP_ASIG_NUM_TEL.PCK_CENTER IS

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
    );

    /*******************************************************************************
    Description: Procedure to update a center
    Author: Andres Felipe Villamizar Collazos
    Date 17-10-2023
    *******************************************************************************/
    PROCEDURE Proc_UpdateCenter (Ip_center_data IN TYP_CENTER_UPDATE);

END PCK_CENTER;
