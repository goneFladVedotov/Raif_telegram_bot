const express = require('express');
const path = require('path');

const app = express();
const port = 8080;
app.use(express.static('public'));
app.get('/admin/', function(req, res) {
  res.sendFile(path.join(__dirname, '/site.html'));
});

app.get('/admin/order/:order_id', function(req, res) {
    res.sendFile(path.join(__dirname, `/fullInfo.html`));
  });

app.get('/admin/refund/:order_id', function(req, res) {
  res.sendFile(path.join(__dirname, `/refund.html`));
});

app.listen(port, function() {
  console.log('Server started at http://localhost:' + port);
});