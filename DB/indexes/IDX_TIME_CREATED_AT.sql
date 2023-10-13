/*******************************************************************************
Description: Index on creation date column (CREATED_AT) to query for the most recent record
Author: Andres Felipe Villamizar Collazos
Date 13-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE INDEX IDX_TIME_CREATED_AT ON APP_ASIG_NUM_TEL.MINIMUM_TIME_SETTING (CREATED_AT);
