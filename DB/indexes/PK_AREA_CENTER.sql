/*******************************************************************************
Description: Primary key constraint for AREA CENTER
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.AREA_CENTER
ADD CONSTRAINT PK_AREA_CENTER
PRIMARY KEY (AREA_ID, CENTER_ID);
