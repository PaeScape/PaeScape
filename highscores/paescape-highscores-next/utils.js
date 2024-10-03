function numberWithCommas(x) {
  if (x === undefined || isNaN(x)) return "";
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function capitalizeEveryWord(username) {
  return username
    .replace(/_/g, " ")
    .replace(/(^\w{1})|(\s+\w{1})/g, (letter) => letter.toUpperCase());
}

function getPageForRank(rank) {
  if (rank === undefined || isNaN(rank)) return 0;
  return Math.ceil(rank / 25);
}

export default {
  numberWithCommas,
  capitalizeEveryWord,
  getPageForRank,
};
