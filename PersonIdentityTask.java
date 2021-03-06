package com.mrhi.class_six.person_identity;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class PersonIdentityTask {
	private static final int ACTION_END 	= 0;
	private static final int ACTION_INSERT 	= 1;
	private static final int ACTION_SECLECT	= 2;
	private static final int ACTION_DELETE 	= 3;
	private static final int ACTION_UPDATE 	= 4;
	private static final int ACTION_SHOW 	= 5;
	private static final int ACTION_SORT 	= 6;
	
	private static DBUtillity dbHelper;
	private static Scanner scanner;
	static {
		PersonIdentityTask.dbHelper = new DBUtillity();
		PersonIdentityTask.scanner = new Scanner( System.in);
	}
	
	public static void main(String[] args) {
		boolean flag = false;	
	
		
		while( !flag) {
			showUi();
			int action = getAction();
			flag = doAction( action);
		}
	}
	
	
	private static void showUi() {
		System.out.println("******************************************");
		System.out.println("1. 입력 2.조회 3.삭제 4.수정 5.출력 6.정렬 0.종료");
		System.out.println("******************************************");
		System.out.println(">> ");
	}
	
	
	private static int getAction() {	
		boolean flag = false;
		int action = -1;
				
		while( !flag) {
			
			try {
			action = PersonIdentityTask.scanner.nextInt();			
			if(action > -2 && action < 7)
				break;
				
			//endl loop
			} catch ( InputMismatchException e) {
				System.out.println("잘못됀 값을 입력 하셨어요!!");
				PersonIdentityTask.scanner.close();
				PersonIdentityTask.scanner = new Scanner(System.in);
				return -1;
			
			}  catch ( Exception e) {
				System.out.println("뭔가 일어났는데... 다시 해주시겠어요?");
				PersonIdentityTask.scanner.close();
				PersonIdentityTask.scanner = new Scanner(System.in);
				return -1;
			}	// endl Exception	
		}
				
		return action;
	}
	
	
	
	private static boolean doAction( int action) {
		boolean isEnd = false;
		
		switch ( action) {
			case ACTION_END: 		isEnd = true; 			break;
			case ACTION_INSERT:		personIdentityInsert(); break;
			case ACTION_SECLECT:	personIdentitySelect();	break;
			case ACTION_DELETE:		personIdentityDelete();	break;		
			case ACTION_UPDATE:		personIdentityUpdate();	break;
			case ACTION_SHOW:		personIdentityShow();	break;
			case ACTION_SORT:		personIdentitSort();	break;
			default:
		}
		
		return isEnd;
	}


	private static void personIdentityInsert() {
		PersonIdentity person = new PersonIdentity();		

		person.setPersonIdentityInput();
		if ( dbHelper.insertPersonIdentity( person))
			System.out.println(person.getPersonName() + " :  삽입함!!");
	}


	private static void personIdentitySelect() {
		final String PATTERN_NAME 		= "^[가-힣]{2,4}$";
		
		
		String queryTarget = null;
		List<PersonIdentity > list = new ArrayList<PersonIdentity >();
		int action = 0;

		//	Get Action
		while(true ) {
			System.out.println("검색 항복(1. ID 2. 이름 0. 나가기)");
			try {
				action = PersonIdentityTask.scanner.nextInt();
				
			} catch (InputMismatchException e) {
				PersonIdentityTask.scanner.close();
				PersonIdentityTask.scanner = new Scanner( System.in);
				action = 0;
			}
			
		
			// compare action
			switch ( action) {
				case DBUtillity.ACTION_ID_QUERY:
					System.out.println("ID 입력");
					break;
				
				case DBUtillity.ACTION_NAME_QUERY:	
					System.out.println("이름 입력");
					break;
					
				default:
					return;
			}
				
			PersonIdentityTask.scanner.nextLine();
			queryTarget = PersonIdentityTask.scanner.nextLine();
				
				
			// Verify Input
			if 		( isVaildValue( queryTarget, PATTERN_NAME)) 	break;
			else if	( queryTarget.length() <= 11 ) 					break;
			System.out.println("올바르게 입력해 주세요!!"); 
		}
		
		
		// SELECT 
		list = dbHelper.selectPersonIdentity(queryTarget, action);
		if( !list.isEmpty()) {
			for(PersonIdentity buf : list) {
				System.out.println( buf.toString());
			}
			return;
		} 
		System.out.println("찾을 대상이 없어요!");
	}


	private static void personIdentityDelete() {
		final String PATTERN_NAME 		= "^[가-힣]{2,4}$";
		
		String queryTarget = new String();
		boolean isSuccess = false;
		int action = 0;
		
		while(true ) {
			//	Get Action
			System.out.println("삭제 항복 (1. ID 2. 이름 0. 나가기)");
			try {
				action = PersonIdentityTask.scanner.nextInt();
				
			} catch ( InputMismatchException e) {
				PersonIdentityTask.scanner.close();
				PersonIdentityTask.scanner = new Scanner( System.in);
				action = 0;
			}
			
			// compare action
			switch ( action) {
				case DBUtillity.ACTION_ID_QUERY:
					System.out.println("ID 입력");
					break;
					
				case DBUtillity.ACTION_NAME_QUERY:	
					System.out.println("이름 입력");
					break;
						
				default:
					return;
			}
			
			// Verify Input
			PersonIdentityTask.scanner.nextLine();
			queryTarget = PersonIdentityTask.scanner.nextLine();
				
			if 		( isVaildValue( queryTarget, PATTERN_NAME)) 	break;
			else if	( queryTarget.length() <= 11) 					break;
			System.out.println("올바르게 입력해 주세요!!"); 
		}
		
		
		
		// DELETE
		isSuccess = dbHelper.deletePersonIdentity(queryTarget, action);
		if( isSuccess) {
			System.out.println("잘 삭제 했습니다!");
			return;
		}
		System.out.println("삭제할 대상이 없어요!");
	}


	private static void personIdentityUpdate() {
		PersonIdentity person = new PersonIdentity();
		
		System.out.print("변경 할 대상의 ");
		person.setPersonIdentityInput();
		
		if ( dbHelper.updatePersonIdentity( person)) {
			System.out.println("잘 변경 했습니다!");
			return;
		}
	}


	private static void personIdentityShow() {
		List<PersonIdentity> personList;
		personList = dbHelper.selectAllPhoneBook();
		
		if( !personList.isEmpty()) {
			for(PersonIdentity buf : personList) {
				
				System.out.println( buf.toString());
			}
		}
	}
	
	
	
	// print to order ASC / DESC
	private static void personIdentitSort() {
		int action = 0;
		List<PersonIdentity> personList = new ArrayList<PersonIdentity>();
		
		
		System.out.println("1:ASC / 2: DESC 0:EXIT");
		System.out.print(">> ");
		try {
			action = PersonIdentityTask.scanner.nextInt();
		
		} catch (InputMismatchException e) {
			System.err.println("error occur");
			PersonIdentityTask.scanner.close();
			PersonIdentityTask.scanner = new Scanner(System.in);
			action = 0;
		}
		
		
		personList = dbHelper.sortPersonIdentity( action);		
		if( !(personList == null)) {

			for(PersonIdentity buf : personList) {
				System.out.println( buf.toString());
			}
		}
	}

	
	// Verify input String
	private static boolean isVaildValue( String value, String filter) {
		
		Pattern pattern = Pattern.compile( filter);
	    Matcher matcher = pattern.matcher( value);
	    if ( matcher.matches()) return true;
		    
	    return false;
	}
}
