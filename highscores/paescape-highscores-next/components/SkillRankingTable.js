import styles from "../styles/HighScores.module.css";
import Image from "next/image";
import Link from "next/link";
import utils from "../utils";

const SkillRankingTable = ({ data }) => {
  let skill = "Overall";
  let img = "blank.gif";
  if (data.skill !== "overall") {
    skill = data.skill;
    img = `skill_icon_${skill}1.gif`;
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
          {utils.capitalizeEveryWord(skill)} Hiscores
        </caption>
        <thead>
          <tr>
            <th className={styles.right}>Rank</th>
            <th className={styles.left}>Name</th>
            <th className={styles.left}>Level</th>
            <th className={styles.right}>XP</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td width={60} />
            <td width={180} />
            <td width={50} />
            <td width={100} />
          </tr>

          {data.rows.map((player, i) => {
            let rowStyles = "";
            if (player.username === data.highlight) {
              rowStyles += styles["personal-hiscores__row--type-highlight"];
            }
            if (player.dead === 1) {
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
                    <a>
                      P{player.prestige}{" "}
                      {utils.capitalizeEveryWord(player.username)}
                    </a>
                  </Link>
                </td>
                <td>{utils.numberWithCommas(player.level)}</td>
                <td className={styles.right}>
                  {utils.numberWithCommas(player.experience)}
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
              pathname:
                skill.toLowerCase() === "overall"
                  ? "/overall"
                  : `/overall/${skill.toLowerCase()}`,
              query: {
                page: data.prevPage,
                gameMode: data.gameMode,
              },
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
              pathname:
                skill.toLowerCase() === "overall"
                  ? "/overall"
                  : `/overall/${skill.toLowerCase()}`,
              query: {
                page: data.nextPage,
                gameMode: data.gameMode,
              },
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

export default SkillRankingTable;
