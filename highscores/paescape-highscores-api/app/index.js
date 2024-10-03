require("dotenv").config();

if(process.env.NODE_ENV === 'development')
  require('./test').createAndPopulateDBTest();

const express = require("express");
// const cors = require("cors");
const app = express();
const api = require("./routes/api");
const morgan = require("morgan");
// var corsOptions = {
//   origin: "http://localhost:3000",
// };
app.use(morgan("tiny"));
// app.use(cors(corsOptions));
// parse requests of content-type - application/json
app.use(express.json());
// don't think I need this
// parse requests of content-type - application/x-www-form-urlencoded
app.use(express.urlencoded({ extended: true }));

app.use("/api/v1", api);

app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ error: true });
});

// catch all dne
app.get("*", function (req, res) {
  res.sendStatus(404);
});

const PORT = process.env.API_PORT || 8080;
const HOST = process.env.API_HOST || "127.0.0.1";
app.listen(PORT, HOST, () => {
  console.log(`HighScores API running on ${HOST}:${PORT}.`);
});
