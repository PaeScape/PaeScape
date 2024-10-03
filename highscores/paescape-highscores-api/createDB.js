const fs = require("fs");
const sqlite = require("better-sqlite3");

function createDB() {
  const dbf = new sqlite("./highscores.db");
  dbf.pragma("journal_mode = WAL");
  dbf.pragma("foreign_keys = ON");
  const createDB = fs.readFileSync("./sql/create.sql", "utf8");
  dbf.exec(createDB);
  dbf.close();
}

createDB();