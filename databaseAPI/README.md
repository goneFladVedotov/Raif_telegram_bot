### Database API
Автор: Федотов Владислав

GET запрос информации о QR по его qrId:
http://localhost:9091/database-api/v1/qrs/{QRid}

GET запрос списка всех QR в БД:
http://localhost:9091/database-api/v1/qrs

GET запрос списка всех статусов оплаты в БД:
http://localhost:9091/database-api/v1/payments

GET запрос информации о статусе возврата по его refundId:
http://localhost:9091/database-api/v1/refund/{refundId}

GET запрос списка всех статусов возврата в БД:
http://localhost:9091/database-api/v1/refund


Планируется добавить:
- механизм авторизации, чтобы сохранять и обновлять информацию могли только клиенты с правами доступа
- хранение информации о платежах
- валидацию входных данных
- улучшенную обработку всех исключений