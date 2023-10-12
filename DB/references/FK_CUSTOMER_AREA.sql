/*******************************************************************************
Description: Foreign key constraint for relationship between CUSTOMER and GEOGRAPHIC_AREA
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.CUSTOMER
ADD CONSTRAINT FK_CUSTOMER_AREA
FOREIGN KEY (AREA_ID) REFERENCES APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA (AREA_ID);
