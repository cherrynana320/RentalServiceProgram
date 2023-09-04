package RentalService;

import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class User implements Serializable {
	private String name; // 이름
	private String phone; // 전화번호
	private String rentalDay; // 대여 날짜
	private String returnDay; // 반납 예정 날짜
	private String[] codeList = new String[3]; // 대여한 물품 코드 배열
	private int[] payList = new int[3]; // 대여 금액 배열
	private int rentalCount = 0; // 대여 물품 코드 / 금액 배열 인덱스 카운트
	Calendar getToday = Calendar.getInstance(); // 오늘 날짜
	
	// 인수 없는 생성자
	User() {}
	
	// 인수 있는 생성자
	User(String name, String phone, String rentalDay, String returnDay)
	{
		this.name = name;
		this.phone = phone;
		this.rentalDay = rentalDay;
		this.returnDay = returnDay;
	}
	
	
	// ++ 고객 관련 정보를 파일객체에 write 하는 함수 구현
	// 입력받은 고객 정보를 읽어서 파일에 적는 함수
//	public void saveUserToFile(ObjectOutputStream oos) throws Exception
//	{
//		// oos 객체로 User 객체를 파일에 저장
//		try {
//			User u = new User(name, phone, rentalDay, returnDay);
//        	oos.writeObject(u);
//			
//			// feedback ++ : dos.writeInt(rentalCount); 대여한 물품의 숫자가 몇개인지
//			// dos.writeInt(rentalCount);
//        	oos.writeObject(rentalCount);
//			for(int i =0; i < rentalCount ; i++) {
//				oos.writeObject(codeList[i]); // 파일에 대여한 물품 코드들 작성 (문자열)
//				oos.writeObject(payList[i]); // 파일에 대여한 물품 가격들 작성 (정수형)
//			}
//		}
//		catch(IOException e) { // Exception 발생 시 해당 문구 출력
//			throw new Exception("파일로 출력할 수 없습니다");
//		}
//	}
	
	
	// 파일에서 고객 정보 읽어오는 함수
	public User readUserFile(ObjectInputStream ois) throws Exception
	{
		// ois 객체로 User 객체를 읽어서
		User u = null;
		try {
			u = (User) ois.readObject();
				
			Integer rentalCount = (Integer) ois.readObject(); // 대여 갯수 읽기
			for (int j = 0; j < rentalCount.intValue(); j++) { // 대여 갯수만큼 반복하여 고객의 대여 배열에 추가
				String rentalProductCode = (String)ois.readObject(); //대여 물품의 코드 읽기
				Integer rentalProductPrice = (Integer)ois.readObject(); //대여 물품의 가격 읽기
				u.addProduct(rentalProductCode, rentalProductPrice); //대여 리스트에 추가
			}
		}
		catch(FileNotFoundException fnfe) {
			throw new Exception("파일이 존재하지 않습니다.");
		}
		catch(IOException e) { // Exception 발생 시 해당 문구 출력 
			throw new Exception("파일로 출력할 수 없습니다");
		}
		return u; // 반환
	}
	
		
	// 이름 반환
	public String getName()
	{
		return name;
	}
	
	// 전화번호 반환
	public String getPhone()
	{
		return phone;
	}
	
	// 대여 일자 반환
	public String getRentalDay()
	{
		return rentalDay;
	}
	
	// 반납 예정 일자 반환
	public String getReturnDay()
	{
		return returnDay;
	}
	
	// 대여 물품 코드 배열 인덱스 카운트 반환
	public int getRentalCount() {
		return rentalCount;
	}
	
	// 대여 물품 코드/ 금액 배열에 코드 추가
	public void addProduct(String code, int money)
	{
		codeList[rentalCount] = code;
		payList[rentalCount] = money;
		rentalCount++;
	}
	
	// 대여 물품 코드 배열 i번째 리턴
	public String codeAt(int i)
	{
		// 대여 물품 코드 배열 i번째 상품 객체 return
		return codeList[i];
	}
	
	// 대여 금액 배열 i번째 리턴
	public int payAt(int i)
	{
		// 대여 금액 배열 i번째 상품 객체 return
		return payList[i];
	}
	
	// 결제 함수
	public int pay() throws Exception
	{
		// 물건 금액 합 계산하기
		int money = 0;
		for(int i = 0; i < rentalCount; i++)
		{
			money += payList[i];
		}
		
		// 반납 일자 입력하기
		getToday.setTime(new Date());
		
		// 대여 일자 불러오기
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(rentalDay);
		Calendar rentalDate = Calendar.getInstance();
		rentalDate.setTime(date1);
		
		// 반납 예정 일자 불러오기
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(returnDay);
		Calendar returnDate = Calendar.getInstance();
		returnDate.setTime(date2);
		
		// 대여하기로 한 기간 계산하기 (반납 예정 일자 - 대여 일자)
		// 밀리초를 차이 구한 후 일 수로 변환
		int day1 = (int) ((returnDate.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));

		// 대여 기간 계산하기 (반납 일자 - 대여 일자)
		// 밀리초를 차이 구한 후 일 수로 변환
		int day2 = (int) ((getToday.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
		
		/* 금액 관련 규칙 설정
		 * 날짜 지켜서 반납 => 상품 금액 총 합 x 대여 기간
		 * 먼저 반납 => 상품 금액 총 합 x 실제 대여 기간(남은 기간은 가격 책정 x)
		 * 나중에 반납 => 상품 금액 총 합 x 실제 대여 기간 + 연체료(하루 대여료) */
		
		// 총 금액 계산하기
		if (day1 == day2) // 반납 예정 날짜에 반납
			return money *= (day1 + 1); // 당일 대여 반납도 1일로 처리
		
		else if (day1 > day2)// 반납 예정 날짜보다 빨리 반납
			return money *= (day2 + 1); // 당일 대여 반납도 1일로 처리
		
		else // 반납 예정 날짜보다 늦게 반납
			return money *= (day2 + 2); // 연체료 (하루 대여료) 추가하여서 반환
	}
	
	@Override
	public boolean equals(Object o) {
		// o가 User 객체가 아니면
		if (!(o instanceof User))
			// false 반환
			return false;
		User u = (User) o;
		// 전화번호가 같으면 true, 다르면 false 반환
		return u.getPhone().equals(this.getPhone());
	}
}