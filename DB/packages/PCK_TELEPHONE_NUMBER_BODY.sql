/*******************************************************************************
Description: Telephone number management package body
Author: Andres Felipe Villamizar Collazos
Date 16-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE PACKAGE BODY APP_ASIG_NUM_TEL.PCK_TELEPHONE_NUMBER IS

  /*******************************************************************************
  Description: Automatic and manual procedure for tracking telephone numbers for complete release
  Author: Andres Felipe Villamizar Collazos
  Date 16-10-2023
  @copyright: TechCamp
  *******************************************************************************/
  PROCEDURE Proc_TrackingNumbers (Ip_Manual IN NUMBER DEFAULT NULL) IS
    BEGIN

    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001, 'Error executing the Proc_TrackingNumbers procedure ' || SQLERRM);
  END Proc_TrackingNumbers;

  /*******************************************************************************
  Description: Procedure for releasing telephone numbers
  Author: Andres Felipe Villamizar Collazos
  Date 16-10-2023
  @copyright: TechCamp
  *******************************************************************************/
  PROCEDURE Proc_ReleaseTelephoneNumber (Ip_Number IN NUMBER) IS
    l_release_date DATE;
    e_already_released EXCEPTION;
    BEGIN
      SELECT RELEASE_DATE
      INTO v_release_date
      FROM TELEPHONE_NUMBER
      WHERE PHONE_NUMBER = Ip_Number
      FOR UPDATE;

      IF v_release_date IS NULL THEN
        UPDATE TELEPHONE_NUMBER
        SET RELEASE_DATE = SYSDATE
        WHERE PHONE_NUMBER = Ip_Number;
      ELSE
        RAISE e_already_released;
      END IF;
      
      COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20001, 'The telephone number does not exist or has not been assigned');
        WHEN e_already_released THEN
          RAISE_APPLICATION_ERROR(-20002, 'The telephone number has already been released');
        WHEN OTHERS THEN
          RAISE_APPLICATION_ERROR(-20003, 'Error executing the Proc_ReleaseTelephoneNumber procedure ' || SQLERRM);
  END Proc_ReleaseTelephoneNumber;

END PCK_TELEPHONE_NUMBER;
