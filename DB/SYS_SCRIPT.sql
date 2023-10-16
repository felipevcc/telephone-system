/*******************************************************************************
Description: Schema creation script with its permissions and tablespace
Author: Andres Felipe Villamizar Collazos
Date 15-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER SESSION SET CURRENT_SCHEMA=SYSTEM;

-- Tablespace creation
@.\sys\tablespaces\TS_APP_ASIG_NUM_TEL.sql

-- User creation
@.\sys\users\APP_ASIG_NUM_TEL.sql

-- Permission creation
@.\sys\permissions\GRANT_TO_SCHEMA.sql
