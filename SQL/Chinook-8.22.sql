CREATE TABLE EXAMPLE(
EX_ID NUMBER PRIMARY KEY,
F_NAME varchar2(30) NOT NULL,
L_NAME varchar2(30) NOT NULL,
FAV_SONG_ID NUMBER,
CONSTRAINT FK_FAV_SONG FOREIGN KEY (FAV_SONG_ID)
REFERENCES TRACK(TRACKID)
);
-- SEQUENCE
CREATE SEQUENCE EX_ID_SEQ
INCREMENT BY 1
START WITH 1;

-- TRIGGER
CREATE OR REPLACE TRIGGER EX_ID_TRIGGER
BEFORE INSERT ON EXAMPLE
FOR EACH ROW
BEGIN
    IF:new.EX_ID is null then
        SELECT EX_ID_SEQ.NEXTVAL INTO :new.EX_ID FROM DUAL;
    END IF;
END;

/

SELECT * FROM DUAL;

SELECT * FROM EXAMPLE;

INSERT INTO EXAMPLE(EX_ID,F_NAME,L_NAME,FAV_SONG_ID)
values(0, 'Bob', 'Marley', 20);

INSERT INTO EXAMPLE(F_NAME,L_NAME,FAV_SONG_ID)
values('Bob', 'Marley', 20);

UPDATE EXAMPLE
SET F_NAME = 'tacos'
WHERE EX_ID = 0;

DELETE FROM EXAMPLE
WHERE EX_ID = 1;

commit;

rollback;