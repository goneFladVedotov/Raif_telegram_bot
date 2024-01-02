## Что реализовано:
1) API может создавать все типы QR. 

Запрос отправляется по адресу: api/v1/qrs/dynamic, api/v1/qrs/static, api/v1/qrs/variable

Локальный порт: 8081

Обязательные параметры у каждого из типов QR можно посмотреть в документации:
https://pay.raif.ru/doc/sbp.html#tag/qr-creation

Также обязательными параметрами у каждого из типов QR являются merchantId и secretKey

2) Добавлена поддержка Swagger
3) Добавлена обработка исключений SbpClient




