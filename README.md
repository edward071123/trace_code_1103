# trace_code_1103：Spring Data JPA 進階 Trace Code

這是一個活動票券訂購系統。專案可以編譯及啟動，但刻意保留 15 個錯誤。

## 技術

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 in-memory database（MySQL mode）
- Maven Wrapper

## 執行

```bash
./mvnw spring-boot:run
```

每次啟動都會重建資料庫。15 個區段會依序執行，單一區段失敗不會阻止後續區段輸出。

## 需求規格

| 題號 | 正確結果 |
|---:|---|
| 01 | Service 能正常呼叫 repository，不發生例外 |
| 02 | Event 101 的票價為 1200 |
| 03 | 可訂購活動只有 101、103、104 |
| 04 | 搜尋 `Spring` 得到 102、103 |
| 05 | `page=1, size=2` 得到 103、104 |
| 06 | Member 2 的歷史訂單只有 `T-DEMO-001` |
| 07 | Event 101 判定為已售出 |
| 08 | 已付款收入為 1200 |
| 09 | 11/03 到 11/10 的活動依時間為 102、103、101 |
| 10 | Member 1 對 Event 103 已有有效訂單 |
| 11 | 關閉 11/09 以前活動時更新 2 筆，Event 102、103 變成 CLOSED |
| 12 | 2 張 Event 103 票的單價／數量／總額為 `600/2/1200` |
| 13 | 0 張票必須被拒絕，booking count 不變 |
| 14 | Event 103 剩餘名額由 10 變成 8 |
| 15 | 模擬付款失敗後，booking count 與 Event 104 剩餘名額都不變 |

## 專案結構

```text
src/main/java/student/ed/tracecode1103/
├── TraceCode1103Application.java
├── constant/
├── entity/
├── repository/
├── runner/
└── service/
```

請以 console 輸出、stack trace、debugger 與 SQL/JPA 行為完成修正。

