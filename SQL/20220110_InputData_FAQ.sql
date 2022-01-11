--○ FAQ(20개)
DECLARE
    V_CNT         NUMBER(9)       := 11;
    
    V_FAQ_NO      FAQ.FAQ_NO%TYPE;
    V_AD_ID       FAQ.AD_ID%TYPE;
    V_QS_NO       FAQ.QS_NO%TYPE;
    V_TITLE       FAQ.TITLE%TYPE;
    V_CONTENT     FAQ.CONTENT%TYPE;

    
BEGIN
    LOOP
        EXIT WHEN V_CNT > 40;
        
        V_CNT := V_CNT + 1;
        
        
        IF (MOD(V_CNT, 6)=0) THEN
			V_AD_ID		:= 'admin1'; 	
			V_QS_NO  	:= 1;
			V_TITLE   	:= '질문 있어요! ' || V_CNT;
			V_CONTENT	:= '대답해드리죠. ' || V_CNT;
          
       
        ELSIF (MOD(V_CNT, 4)=0) THEN
			V_AD_ID		:= 'admin2'; 	
			V_QS_NO  	:= 2;
			V_TITLE   	:= '궁금해요! ' || V_CNT;
			V_CONTENT	:= '답은 이 안에 있습니다. ' || V_CNT;
          
       
        ELSIF (MOD(V_CNT, 3)=0) THEN
			V_AD_ID		:= 'admin1'; 	
			V_QS_NO  	:= 3;
			V_TITLE   	:= '알고 싶어요! ' || V_CNT;
			V_CONTENT	:= '답변 대공개! ' || V_CNT;
           
           
        ELSE
			V_AD_ID		:= 'admin2'; 	
			V_QS_NO  	:= 4;
			V_TITLE   	:= '자주 묻는 질문이에요! ' || V_CNT;
			V_CONTENT	:= '해결해드리죠. ' || V_CNT;
            
            
        END IF;
        
        
        -- 실행
        INSERT INTO FAQ(FAQ_NO, AD_ID, QS_NO, TITLE, CONTENT) 
        VALUES(FAQESEQ.NEXTVAL, V_AD_ID , V_QS_NO , V_TITLE, V_CONTENT);
        
        
    END LOOP;
END;



SELECT *
FROM VIEW_FAQ;

COMMIT;