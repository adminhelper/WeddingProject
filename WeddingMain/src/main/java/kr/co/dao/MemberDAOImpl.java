package kr.co.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Inject SqlSession sql;
	// 회원가입

	@Override
	public void register(MemberVO vo) throws Exception {
		sql.insert("memberMapper.register", vo);
	}
	
	@Override
	public MemberVO login(MemberVO vo) throws Exception{
		return sql.selectOne("memberMapper.signin", vo);
	}
	
	    
	//서비스에서 보낸 파라미터들을 memberUpdate(MemberVO vo)에 담음.
	@Override
	public void memberUpdate(MemberVO vo) throws Exception {
		// vo에 담긴 파라미터들은 memberMapper.xml에 memberMapper라는 namespace에 
		// 아이디가 memberUpdate인 쿼리에 파라미터들을 넣어줌.
		sql.update("memberMapper.memberUpdate", vo); 
	}
	
	@Override
	public void memberDelete(MemberVO vo) throws Exception {
		// MemberVO에 담긴 값들을 보내줍니다.
		// 그럼 xml에서 memberMapper.memberDelete에 보시면
		//  #{userId}, #{userPass}에 파라미터값이 매칭이 되겠지요.
		sql.delete("memberMapper.memberDelete", vo);
		
	}
	
	//패스워드 체크
	@Override
	public int passChk(MemberVO vo) throws Exception{
		int result = sql.selectOne("memberMapper.passChk",vo);
		return result;
	}
	
	// 아이디 중복 체크
	@Override
	public int idChk(MemberVO vo) throws Exception{
		int result = sql.selectOne("memberMapper.checkOverId", vo);
		return result;
	}
	
	// 이메일 중복 체크
	@Override
	public int checkEmail(MemberVO vo) throws Exception{
		int result = sql.selectOne("memberMapper.checkEmail",vo);
		return result;
	}
	
	   // 정보수정전 pw 확인
	   @Override
	   public boolean checkPw(String userId , String userPass) {
	      boolean result = false;
	      Map<String, String >map = new HashMap<String, String>();
	      map.put("userId", userId);
	      map.put("userPass", userPass);
	      int count = sql.selectOne("memberMapper.checkPw", map);
	      if(count==1) result = true;
	      return result;
	   }
		
}
