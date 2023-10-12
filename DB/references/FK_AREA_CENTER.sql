/*******************************************************************************
Description: Foreign key constraint for relationship between GEOGRAPHIC_AREA and CENTER
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.AREA_CENTER
ADD CONSTRAINT FK_AREA_CENTER_AREA
FOREIGN KEY (AREA_ID) REFERENCES APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA (AREA_ID);

ALTER TABLE APP_ASIG_NUM_TEL.AREA_CENTER
ADD CONSTRAINT FK_AREA_CENTER_CENTER
FOREIGN KEY (CENTER_ID) REFERENCES APP_ASIG_NUM_TEL.CENTER (CENTER_ID);
