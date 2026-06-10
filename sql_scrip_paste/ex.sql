CREATE TABLE NEW_BOARD (
                           NO NUMBER CONSTRAINT new_board_no_pk PRIMARY KEY,
                           title varchar2(300) CONSTRAINT new_board_title_nn NOT NULL,
                           content clob CONSTRAINT new_board_content_nn NOT NULL,
                           hit NUMBER DEFAULT 0,
                           regdate DATE DEFAULT sysdate);
