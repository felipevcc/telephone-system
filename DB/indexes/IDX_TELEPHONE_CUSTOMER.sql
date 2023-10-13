/*******************************************************************************
Description: Index in the customer id column to query its assigned telephone number
Author: Andres Felipe Villamizar Collazos
Date 13-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE INDEX IDX_TELEPHONE_CUSTOMER ON APP_ASIG_NUM_TEL.TELEPHONE_NUMBER (CUSTOMER_ID);
