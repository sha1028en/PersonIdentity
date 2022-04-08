# PersonIdentity



## Description
각 학생들의 신상정보를 편하게 관리하자.
기존의 File로 관리하기는 너무 불편하니까.
MySQL을 활용해서 편하게 관리하였다.
* 기능으로는
  * 학생 삽입
  * 학생 조회
  * 학생 삭제
  * 학생 수정
  * 학생 출력( 전원 )
  * 학생 정렬( 출력 )

이 있다.

## Prerequisite
mysql-connector-java-5.1.42-bin.jar 필요!

## Files
 * PersonIdentity     : Model, SQL 질의할 때 쓰는 구조다.
 * PersonIdentityTask : View, 여기에 Main(...)이 계신다.
 * DBConnector        : Controler, MySQL을 활성화(초기화 ) 한다.
 * DBUtillity         : Controler, MySQL의 질의를 담당한다.


## Usage : PersonIdentity : Model
```

 private String personId; 		
	private String personName;		
	private int personAge;		
 
	private int pointLangC;		
	private int pointLangJava;		
	private int pointLangSql;		
	private int pointSum;			
	private double pointAvg;		
	private String pointGrade;		
	
	private final int LIMIT_POINT_MAX = 100;
	private final int LIMIT_POINT_MIN = 0;
 
  ...
 public PersonIdentity setPersonIdentityInput() 
 public PersonIdentity initPersonIdentity()
```

* personId, Name, Age
  * 각각 학생의 학번 / 이름 / 나이를 담는다.
```
```
* pointLangC, Java, Sql, Sum, Avg, Grade
  * 각각 학생의 과목점수 3개 / 3과목 총점 / 3과목 평균 / 3과목 등급이다.
  * 총점 / 평균 / 등급 은 initPersonIdentity()를 부르면 나머지 3과목 값으로 알아서 계산한다.
```
```
* private final int LIMIT_POINT_MAX = 100;
* private final int LIMIT_POINT_MIN = 0;
  * 과목의 최대 / 최저 점수를 제한하고 있다.
```
```
* public PersonIdentity setPersonIdentityInput() 
  * 해당 객체의 맴버들을 키보드로 입력받아 객체 자체를 리턴한다.
```
```
* public PersonIdentity initPersonIdentity()
  * 해당 객체의 과목 점수가 변동돼었을 경우. 해당 메서드를 call 해주자
  * 알아서 3과목의 값으로 총점 / 평균 / 등급을 계산한다
  * 해당 객체 자체를 리턴한다. 



## Usage : DBControler : Controler

 ```
 public DBConnector() {}
  
 
  public Connection initConnection()
 
   final String LOCATION = 
		  "src\\com\\mrhi\\class_six\\person_identity\\person_identity.properties";
				
		 final Properties inPrivate = new Properties();
		 final FileReader inStream = new FileReader( LOCATION);
		 inPrivate.load( inStream);
		
		
		 final String DRIVER = inPrivate.getProperty("Driver");
		 final String URL	= inPrivate.getProperty("URL");AS
		 final String UID	= inPrivate.getProperty("UID");
		 final String PASSWD	= inPrivate.getProperty("PASSWD");
	
	
			Class.forName( DRIVER);
			connection = (Connection ) DriverManager.getConnection( URL, UID, PASSWD);

    ....
    
    return connection
       
 ```
 
 해당 객체는 MySQL을 초기화 한 객체를 리턴한다.
 
  * LOCATION은 DB를 구동할 정보를 담고있는 프로퍼티의 경로.
  * DRIVER는 MySQL이 구동할 드라이버 경로
  * URL은 MySQL의 db명
  * UID는 MySQL 사용자명
  * PASSWD는 MySQL 사용자의 패스워드
 
 ## Usage : DBUtillity : Controler
```
  ...
 
  public static final int ACTION_ID_QUERY = 1;
 	public static final int ACTION_NAME_QUERY = 2;
 	public static final int ACTION_SORT_ASC = 1;
 	public static final int ACTION_SORT_DESC = 2;
 
  ...
  public boolean insertPersonIdentity( PersonIdentity person)
  public boolean updatePersonIdentity( PersonIdentity person)
  public boolean deletePersonIdentity(String queryTarget, int action)
  public List<PersonIdentity> selectPersonIdentity( String queryTarget ,int action)
  public List<PersonIdentity> selectAllPhoneBook()
  public List<PersonIdentity > sortPersonIdentity(int action)
```

각 메서드 들은 PersonIdentity 객체의 값을 기반으로 쿼리를 진행한다.

내부적으로 DBConnection.initConnection()을 사용하여 DB를 접근하고
PersonIdentity ArrayList 또는 쿼리 성공여부를 리턴한다.


* boolean insertPersonIdentity( PersonIdentity person)
  * 실제로 DB에 PersonIdentity 삽입을 시도하고
  * 쿼리 결과를 true / false로 반환한다.
```
```
* public boolean updatePersonIdentity( PersonIdentity person)
  * PersonIdentity.personId를 기준으로 나머지 맴버들을 update 시도한다
  * 쿼리 결과를 true / false로 리턴한다. 
```
```
* public boolean deletePersonIdentity(String queryTarget, int action)
  *  파라미터의 action이 ACTION_ID_QUERY / ACTION_NAME_QUERY 이냐에 따라 쿼리 기준이 다르다.
  *  action을 기반으로 PersonIdentity의 personId / personName을 기준으로 delete 를 시도한다
  *  쿼리 결과를 true / false로 리턴한다.
```
```
* public List<PersonIdentity> selectPersonIdentity( String queryTarget ,int action)
  * 파라미터의 action이 ACTION_ID_QUERY / ACTION_NAME_QUERY 이냐에 따라 쿼리 기준이 다르다.
  * action을 기반으로 PersonIdentity의 personId / personName을 기준으로 select 를 시도한다
  * 쿼리 결과를 ArrayList< PersonIdentity>로 리턴하거나 실패시 null을 리턴한다.
 ```
 ```
* public List<PersonIdentity> selectAllPhoneBook()
  * 모든 레코드를 select 시도한다
  * 쿼리 결과를 ArrayList< PersonIdentity>로 리턴하거나 실패시 null을 리턴한다.
 ```
 ```
 * public List<PersonIdentity > sortPersonIdentity(int action)
   * 파리미터의 action이 ACTION_SORT_ASC / ACTION_SORT_DESC냐 따라 쿼리 기준이 다르다.
   * PersonIdentity.pointTotal을 기준으로 오름 / 내림 정렬하여 select 를 시도한다.
   * 쿼리 결과를 ArrayList< PersonIdentity>로 리턴하거나 실패시 null을 리턴한다.
 ```
 ```




###### 20220/04/08
