# Monicar 

## 렌터카 차량 관제 서비스 (테크돔 기업 연계 프로젝트)
[모니카 서비스](www.monicar.store)

![login-page.png](img/login-page.png)

## 서비스 소개
저희 `monicar`는 렌터카 차량 관제 서비스입니다.
적어도 15000대 이상의 애뮬레이터가 시동on, GPS정보, 시동off를 http로 요청합니다.
특히, GPS 정보는 http 연결 비용을 줄이기 위해 60초마다 60개의 데이터를 요청합니다.

요청한 데이터를 수집서버가 저장하여, 업체별로 차량의 정보를 조회할 수 있습니다.

차량의 운행정보, 위치, 과거 경로 조회, 실시간 경로 조회 등을 기반으로 차량을 관제할 수 있습니다.

![흐름.png](img/흐름.png)

## 주요 기능

## 시스템 아키텍처
![아키텍처.png](img/아키텍처.png)

## 팀원소개
<table>
  <tbody>
    <tr>
      <td align="center">
        <a href="https://github.com/Suxxxxhyun">
          <img src="img/수현.png" width="100px;" alt="팀장 프로필"/><br />
          <sub><b>팀장 : 박수현</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/kbyunghoon">
          <img src="img/병훈.png" width="100px;" alt="BE 팀원 프로필"/><br />
          <sub><b>BE 팀원 : 김병훈</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/tomatozil">
          <img src="img/지윤.png" width="100px;" alt="BE 팀원 프로필"/><br />
          <sub><b>BE 팀원 : 윤지윤</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/red-dev-Mark">
          <img src="img/혁준.png" width="100px;" alt="FE 팀원 프로필"/><br />
          <sub><b>FE 팀원 : 권혁준</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/nanafromjeju">
          <img src="img/난아.png" width="100px;" alt="FE 팀원 프로필"/><br />
          <sub><b>FE 팀원 : 김난아</b></sub>
        </a>
      </td>
    </tr>
  </tbody>
</table>




## 프로젝트 기획
- [피그마 링크]()
- [API명세서](https://www.notion.so/API-2651629b10674069b0500e3ea8aa1a0f?pvs=4)

## ERD
![ERD.png](img/ERD.png)

## 기술 스택
### BE
<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=github,docker,spring,java,kafka,aws,mysql,redis,elasticsearch" />
  </a>
</p>

<p align="center">
  ⚡ Tech Stack: Spring Boot, Spring Data JPA, Spring Security, QueryDSL, MyBatis, AWS EC2, AWS RDS, AWS ALB
</p>

### FE
<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=nextjs,typescript,vercel" />
  </a>
</p>

<p align="center">
  🗺️ Tech Stack: Nextjs,Typescript,Vercel, Kakao Map 
</p>

## BE Trouble Shooting
- [카프카 도입이유](img/Kafka도입이유.md)
- [Route53 동작이 제대로 안될 때가 있다.](https://github.com/Kernel360/blog/pull/131)
- [부하테스트 - 메시지 큐를 도입할 수 밖에 없었던 이유](img/부하테스트.md)

## 기술 세미나
- [확장가능한 시스템 설계 - 박수현](https://docs.google.com/presentation/d/179fQnnWuqpqkAJLTbvhTNh4YNEe4cjSUiS6bVxZVHAY/edit?usp=sharing)
- [동기 비동기 HTTP 요청 - 윤지윤](https://docs.google.com/presentation/d/1aIru1TdHdLZ956GhZVdg9CFyTxlelEdOxnKPFwaDa2M/edit?usp=sharing)



