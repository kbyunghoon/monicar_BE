# Monicar 

## 렌터카 차량 관제 서비스 (테크돔 기업 연계 프로젝트)
[모니카 서비스](https://www.monicar.store)

![login-page.png](img/login-page.png)

## 서비스 소개 👉 [홍보영상](https://drive.google.com/file/d/10xEFUyXy97PrYu2DSVRhHNsbYHQz9EzK/view?usp=drive_link)
저희 `monicar`는 렌터카 차량 관제 서비스입니다.
적어도 15000대 이상의 애뮬레이터가 시동on, GPS정보, 시동off를 http로 요청합니다.
특히 GPS 정보는, 받는 서버의 부하를 줄이기 위해 1초마다가 아닌, 60초마다 60개의 데이터를 요청합니다.
카프카를 통해 차량의 정보가 올바르게 저장이 되면 업체별로 차량의 운행정보, 위치, 과거 경로 조회, 실시간 경로 조회 등을 기반으로 차량을 관제할 수 있습니다.

## 주요 기능

**대시보드**

![대시보드](https://github.com/user-attachments/assets/73b6af4b-ebc6-43d6-b4ba-0e3752ea90d1)

**공지사항**

![공지사항](https://github.com/user-attachments/assets/25e366aa-22a2-476b-ab1b-f7f0f00b5db5)

**위치조회**

![위치조회](https://github.com/user-attachments/assets/242e15a2-3265-48d5-b4d1-0f498434296d)

**경로조회**

![경로조회](https://github.com/user-attachments/assets/11655278-c573-4f72-8db1-5d548055fcc8)


**운행기록>운행일지**

![운행기록 운행일지](https://github.com/user-attachments/assets/fdade107-2486-4d20-9385-3451934cdc4e)

**운행기록>차량등록**

![운행기록 차량등록](https://github.com/user-attachments/assets/ed03ab16-d288-4bce-b326-5af7ce355dfa)

**알림창>점검현황**

![알림창 점검현황](https://github.com/user-attachments/assets/65f19cc4-3acc-42ed-a835-5cf3ce635870)



## 시스템 아키텍처
![아키텍처.png](img/아키텍처.png)

---

## 위 아키텍처를 도입한 이유
![흐름.png](img/흐름.png)
요청한 데이터를 단일 수집서버로는 한계가 있으며, [부하테스트의 결과](https://www.canva.com/design/DAGfcRy6xGE/q6HvKo_qZ0ftXHH79zK6rg/edit?utm_content=DAGfcRy6xGE&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)로 인해 메시지 큐를 도입할 수 밖에 없었습니다.

![최종데이터흐름.png](img/최종데이터흐름.png)
CPU 70 이상이 되면 Scale Out, 30이하면 Scale in이 되도록 Auto Scaling을 설정하였습니다.
애뮬레이터 서버가 60초마다 요청한 60개의 데이터가 카프카를 통해 저장하여, 업체별로 차량의 정보를 조회할 수 있습니다.
Consumer Server가 현재 JPA saveall로 구현되어있어, 6시간 지연되어 적재되는 이슈를 발견하였습니다.
실시간 차량을 보여주기 위해, Consumer서버에서 mybatis의 bulk insert기능을 활용하여 데이터를 더 빠르게 적재될 수 있도록 할 예정입니다.

## 프로젝트 기획
- [피그마 링크]()
- [API명세서](https://www.notion.so/API-2651629b10674069b0500e3ea8aa1a0f?pvs=4)

## ERD
![ERD.png](img/ERD.png)

## 기술 스택
### BE
<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=github,docker,spring,java,kafka,aws,mysql,redis,elasticsearch,jwt" />
  </a>
</p>

<p align="center">
  ⚡ Tech Stack: Spring Boot, Spring Data JPA, Spring Security, QueryDSL, MyBatis, JWT, SSE, AWS EC2, AWS RDS, AWS ALB
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
- [부하테스트 - 메시지 큐를 도입할 수 밖에 없었던 이유](https://www.canva.com/design/DAGfcRy6xGE/q6HvKo_qZ0ftXHH79zK6rg/edit?utm_content=DAGfcRy6xGE&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
- [메시지 큐 중에 Kafka를 도입한 이유 - 구체적으로 업로드 예정](https://github.com/Kernel360/KDEV3_monicar_BE/blob/develop/img/Kafa도입이유.md)
- [Route53 동작이 제대로 안될 때가 있다. - 수정 예정](https://github.com/Kernel360/blog/pull/131)
- [SSE 응답이 계속 대기 중(Pending)으로 유지되는 문제](https://github.com/Kernel360/KDEV3_monicar_BE/wiki/SSE-%EC%9D%91%EB%8B%B5%EC%9D%B4-%EA%B3%84%EC%86%8D-%EB%8C%80%EA%B8%B0-%EC%A4%91(Pending)%EC%9C%BC%EB%A1%9C-%EC%9C%A0%EC%A7%80%EB%90%98%EB%8A%94-%EB%AC%B8%EC%A0%9C)
- [Spring Transaction ‐ Propagation.REQUIRES_NEW 를 써보며 - 수정 예정](https://github.com/Kernel360/KDEV3_monicar_BE/wiki/Spring-Transaction-%E2%80%90-Propagation.REQUIRES_NEW-%EB%A5%BC-%EC%8D%A8%EB%B3%B4%EB%A9%B0)

## 최종 발표
- [최종 발표 자료](https://drive.google.com/file/d/1sYwH2P9kXGQt7P9W2PiP7xit3rH_jNVc/view?usp=drive_link)

## 기술 세미나
- [확장가능한 시스템 설계 - 박수현](https://docs.google.com/presentation/d/179fQnnWuqpqkAJLTbvhTNh4YNEe4cjSUiS6bVxZVHAY/edit?usp=sharing)
- [동기 비동기 HTTP 요청 - 윤지윤](https://docs.google.com/presentation/d/1aIru1TdHdLZ956GhZVdg9CFyTxlelEdOxnKPFwaDa2M/edit?usp=sharing)

## 팀원소개
<table>
  <tbody>
    <tr>
      <td align="center">
        <a href="https://github.com/Suxxxxhyun">
          <img src="img/수현.png" width="100px;" alt="팀장 프로필"/><br />
          <sub><b>팀장(BE, Infra) : 박수현</b></sub>
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



