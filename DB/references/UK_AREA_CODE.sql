/*******************************************************************************
Description: Unique constraint on the CODE column of the GEOGRAPHIC_AREA table
Author: Andres Felipe Villamizar Collazos
Date 13-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA
ADD CONSTRAINT UK_AREA_CODE UNIQUE (CODE);
