/*******************************************************************************
Description: Telephone number management package
Author: Andres Felipe Villamizar Collazos
Date 16-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE APP_ASIG_NUM_TEL.PCK_TELEPHONE_NUMBER IS

  /*******************************************************************************
  Description: Automatic and manual procedure for tracking telephone numbers for complete release
  Author: Andres Felipe Villamizar Collazos
  Date 16-10-2023
  @copyright: TechCamp
  *******************************************************************************/
  PROCEDURE Proc_TrackingNumbers (Ip_Manual IN NUMBER DEFAULT NULL);

  /*******************************************************************************
  Description: Procedure for releasing telephone numbers
  Author: Andres Felipe Villamizar Collazos
  Date 16-10-2023
  @copyright: TechCamp
  *******************************************************************************/
  PROCEDURE Proc_ReleaseTelephoneNumber (Ip_Number IN NUMBER);

END PCK_TELEPHONE_NUMBER;
