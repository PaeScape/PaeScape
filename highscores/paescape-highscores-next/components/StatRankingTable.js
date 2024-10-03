import styles from "../styles/HighScores.module.css";
import Image from "next/image";
import Link from "next/link";
import constants from "../constants";
import utils from "../utils";

const StatRankingTable = ({ data }) => {
  let stat = "";
  let img = "blank.gif";
  if (data.stat !== undefined) {
    stat = data.stat;
    img = `game_icon_${constants.STATS[stat]
      .toLowerCase()
      .replace(/ /g, "")
      .replace(/\(/g, "")
      .replace(/\)/g, "")
      .replace(/-/g, "")}.png`;
  }
  return (
    <div className={styles.contentHiscores}>
      <table>
        <caption>
          <Image
            className={styles.miniimg}
            src={`/img/${img}`}
            height={"16px"}
            width={"16px"}
            alt=""
          />
          {constants.STATS[stat]} Hiscores
        </caption>
        <thead>
          <tr>
            <th className={styles.right}>Rank</th>
            <th className={styles.left}>Name</th>
            <th className={styles.right}>Value</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td width={60} />
            <td width={120} />
            <td width={100} />
          </tr>

          {data.rows.map((player) => {
            let rowStyles = "";
            if (player.username === data.highlight) {
              rowStyles += styles["personal-hiscores__row--type-highlight"];
            }
            if (data.gameMode === "HARDCORE_IRONMAN" && player.dead) {
              rowStyles += ` ${styles["personal-hiscores__row--dead"]}`;
            }
            return (
              <tr className={rowStyles} key={player.username}>
                <td className={styles.right}>{player.playerRank}</td>
                <td className={styles.left}>
                  <Link
                    href={{
                      pathname: `/personal/${player.username.replace(
                        / /g,
                        "_"
                      )}`,
                      query: { gameMode: data.gameMode.toLowerCase() },
                    }}
                  >
                    <a>{utils.capitalizeEveryWord(player.username)}</a>
                  </Link>
                </td>
                <td className={styles.right}>
                  {utils.numberWithCommas(player.statValue)}
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>

      <nav className={styles["personal-hiscores__pagination"]}>
        {data.prevPage !== 0 && (
          <Link
            href={{
              pathname: `/stat/${stat.toLowerCase()}`,
              query: { page: data.prevPage },
            }}
          >
            <a title="Scroll up table">
              <Image
                src="/img/arrow_up.gif"
                alt="Scroll Up"
                height={23}
                width={22}
              />
            </a>
          </Link>
        )}
        {data.nextPage !== 0 && (
          <Link
            href={{
              pathname: `/stat/${stat.toLowerCase()}`,
              query: { page: data.nextPage },
            }}
          >
            <a title="Scroll down table">
              <Image
                src="/img/arrow_down.gif"
                alt="Scroll Down"
                height={23}
                width={22}
              />
            </a>
          </Link>
        )}
      </nav>
    </div>
  );
};

export default StatRankingTable;
