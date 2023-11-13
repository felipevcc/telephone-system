/*******************************************************************************
Description: Schema creation script (database creation)
Author: Andres Felipe Villamizar Collazos
Date 16-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER SESSION SET CURRENT_SCHEMA=SYSTEM;

ALTER SESSION SET "_ORACLE_SCRIPT"=true;

-- Tablespace creation
@.\sys\tablespaces\TS_APP_ASIG_NUM_TEL.sql

-- User creation
@.\sys\users\APP_ASIG_NUM_TEL.sql

-- Permission creation
@.\sys\permissions\GRANT_TO_SCHEMA.sql


ALTER SESSION SET CURRENT_SCHEMA=APP_ASIG_NUM_TEL;

-- Table creation
@.\tables\GEOGRAPHIC_AREA.sql
@.\tables\CENTER.sql
@.\tables\AREA_CENTER.sql
@.\tables\CUSTOMER_TYPE.sql
@.\tables\DOCUMENT_TYPE.sql
@.\tables\CUSTOMER.sql
@.\tables\TELEPHONE_NUMBER.sql
@.\tables\TELEPHONE_NUMBER_AUDIT.sql
@.\tables\MINIMUM_TIME_SETTING.sql

-- Sequence creation
@.\sequences\SEQ_CENTER.sql
@.\sequences\SEQ_CUSTOMER_TYPE.sql
@.\sequences\SEQ_CUSTOMER.sql
@.\sequences\SEQ_DOCUMENT_TYPE.sql
@.\sequences\SEQ_GEOGRAPHIC_AREA.sql
@.\sequences\SEQ_MINIMUM_TIME_SETTING.sql
@.\sequences\SEQ_TELEPHONE_NUMBER_AUDIT.sql
@.\sequences\SEQ_TELEPHONE_NUMBER.sql

-- Primary key creation
@.\indexes\PK_AREA_CENTER.sql
@.\indexes\PK_CENTER.sql
@.\indexes\PK_CUSTOMER_TYPE.sql
@.\indexes\PK_CUSTOMER.sql
@.\indexes\PK_DOCUMENT_TYPE.sql
@.\indexes\PK_GEOGRAPHIC_AREA.sql
@.\indexes\PK_MINIMUM_TIME_SETTING.sql
@.\indexes\PK_TELEPHONE_NUMBER_AUDIT.sql
@.\indexes\PK_TELEPHONE_NUMBER.sql

-- Index creation
@.\indexes\IDX_CUSTOMER_DOCUMENT.sql
@.\indexes\IDX_TELEPHONE_AUDIT_CUSTOMER.sql
@.\indexes\IDX_TELEPHONE_AUDIT_NUMBER.sql
@.\indexes\IDX_TELEPHONE_CUSTOMER.sql
@.\indexes\IDX_TELEPHONE_NUMBER.sql
@.\indexes\IDX_TIME_CREATED_AT.sql

-- Foreign key creation
@.\references\FK_AREA_CENTER.sql
@.\references\FK_CUSTOMER_AREA.sql
@.\references\FK_CUSTOMER_CUSTOMER_TYPE.sql
@.\references\FK_CUSTOMER_DOCUMENT_TYPE.sql
@.\references\FK_TELEPHONE_AUDIT_CENTER.sql
@.\references\FK_TELEPHONE_AUDIT_CUSTOMER.sql
@.\references\FK_TELEPHONE_CENTER.sql
@.\references\FK_TELEPHONE_CUSTOMER.sql

-- Constraint creation
@.\references\UK_AREA_CODE.sql
@.\references\UK_CENTER_NAME.sql
@.\references\UK_CUSTOMER_DOCUMENT.sql
@.\references\UK_TELEPHONE_CUSTOMER.sql
@.\references\UK_TELEPHONE_NUMBER.sql
@.\references\CK_TELEPHONE_AUDIT_ACTIVE.sql

-- Package creation
@.\packages\PCK_CENTER.sql
/
@.\packages\PCK_CENTER_BODY.sql
/
@.\packages\PCK_CUSTOMER.sql
/
@.\packages\PCK_CUSTOMER_BODY.sql
/
@.\packages\PCK_TELEPHONE_NUMBER.sql
/
@.\packages\PCK_TELEPHONE_NUMBER_BODY.sql
/

-- Job creation
@.\sql\JOB_TRACKING_NUMBERS.sql
/

-- Record insertion
@.\sql\INSERT_CUSTOMER_TYPES.sql
/
@.\sql\INSERT_DOCUMENT_TYPES.sql
/
@.\sql\INSERT_MINIMUM_TIME_SETTING.sql
/
@.\sql\INSERT_AREAS.sql
/
@.\sql\INSERT_CENTERS.sql
/
@.\sql\INSERT_AREA_CENTER.sql
/
