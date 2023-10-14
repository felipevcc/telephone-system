/*******************************************************************************
Description: Unique constraint on the DOCUMENT column of the CUSTOMER table
Author: Andres Felipe Villamizar Collazos
Date 13-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.CUSTOMER
ADD CONSTRAINT UK_CUSTOMER_DOCUMENT UNIQUE (DOCUMENT);
