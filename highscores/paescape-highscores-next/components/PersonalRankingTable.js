import styles from "../styles/HighScores.module.css";
import Image from "next/image";
import Link from "next/link";
import utils from "../utils";
import constants from "../constants";

const PersonalRankingTable = ({ data }) => {
  let img = "blank.gif";
  if (data.player !== undefined) {
    img = `rank_icon_${data.player.staffRights.toLowerCase()}.png`;
  }
  return (
    <div className={styles.contentHiscores}>
      {!data.player && (
        <div align="center">
          No player <b>&quot;{data.notFound}&quot;</b> found
        </div>
      )}
      {data.player && (
        <table
          style={{ maxWidth: 355 }}
          cellSpacing={0}
          cellPadding={3}
          border={0}
        >
          <tbody>
            <tr>
              <td colSpan={5} align="center">
                <b>{`Personal scores for `}</b>
                {data.player.staffRights &&
                  data.player.staffRights !== "PLAYER" && (
                    <Image
                      src={`/img/${img}`}
                      height={"12px"}
                      width={"12px"}
                      alt=""
                    />
                  )}
                <b>{`P${data.player.prestige} ${utils.capitalizeEveryWord(
                  data.player.username
                )}`}</b>
              </td>
            </tr>
            <tr>
              <td colSpan={2} style={{ textAlign: "left", paddingLeft: 24 }}>
                <b>Skill</b>
              </td>
              <td align="right">
                <b>Rank</b>
              </td>
              <td align="right">
                <b>Level</b>
              </td>
              <td align="right">
                <b>XP</b>
              </td>
            </tr>
            <tr>
              <td width={35} />
              <td width={100} />
              <td width={75} />
              <td width={40} />
              <td width={75} />
            </tr>
            <tr>
              <td />
              <td align="left">
                <a
                  href={`/overall?page=${utils.getPageForRank(
                    data.player["totalRank"]
                  )}&gameMode=${
                    data.player.gameMode
                  }&player=${data.player.username.replace(/ /g, "_")}`}
                >
                  Overall
                </a>
              </td>
              <td align="right">
                {utils.numberWithCommas(data.player["totalRank"])}
              </td>
              <td align="right">
                {utils.numberWithCommas(data.player["totalLevel"])}
              </td>
              <td align="right">
                {utils.numberWithCommas(data.player["totalExp"])}
              </td>
            </tr>
            {constants.SKILLS.map((skill) => {
              return (
                <tr key={skill}>
                  <td align="right">
                    <Image
                      className={styles.miniimg}
                      src={`/img/skill_icon_${skill}1.gif`}
                      alt=""
                      height={"16px"}
                      width={"16px"}
                    />
                  </td>
                  <td align="left">
                    <Link
                      href={`/overall/${skill}?page=${utils.getPageForRank(
                        data.player[skill + "Rank"]
                      )}&gameMode=${data.player.gameMode.toLowerCase()}&player=${data.player.username.replace(
                        / /g,
                        "_"
                      )}`}
                    >
                      <a>{utils.capitalizeEveryWord(skill)}</a>
                    </Link>
                  </td>
                  <td align="right">
                    {utils.numberWithCommas(data.player[skill + "Rank"])}
                  </td>
                  <td align="right">
                    {utils.numberWithCommas(data.player[`${skill}Level`])}
                  </td>
                  <td align="right">
                    {utils.numberWithCommas(data.player[`${skill}Exp`])}
                  </td>
                </tr>
              );
            })}
            <tr style={{ height: 40, verticalAlign: "bottom" }}>
              <th colSpan={2} style={{ textAlign: "left", paddingLeft: 24 }}>
                Stat
              </th>
              <th align="right">Rank</th>
              <th colSpan={2} align="right">
                Value
              </th>
            </tr>
            {Object.keys(constants.STATS).map((stat) => {
              if (!data.player[stat + "Rank"]) return;
              return (
                <tr key={stat}>
                  <td align="right">
                    <Image
                      className={styles.miniimg}
                      height={"16px"}
                      width={"16px"}
                      src={`/img/game_icon_${constants.STATS[stat]
                        .toLowerCase()
                        .replace(/ /g, "")
                        .replace(/'/g, "")
                        .replace(/\(/g, "")
                        .replace(/\)/g, "")
                        .replace(/-/g, "")}.png`}
                      alt=""
                    />
                  </td>
                  <td>
                    <Link
                      href={`/stat/${stat}?page=${utils.getPageForRank(
                        data.player[stat + "Rank"]
                      )}&gameMode=${
                        data.player.gameMode
                      }&player=${data.player.username.replace(/ /g, "_")}`}
                    >
                      <a>{constants.STATS[stat]}</a>
                    </Link>
                  </td>
                  <td align="right">
                    {utils.numberWithCommas(data.player[stat + "Rank"])}
                  </td>
                  <td colSpan={2} align="right">
                    {utils.numberWithCommas(data.player[stat])}
                  </td>
                </tr>
              );
            })}
            <tr style={{ height: 40, verticalAlign: "bottom" }}>
              <th colSpan={2} style={{ textAlign: "left", paddingLeft: 24 }}>
                Boss
              </th>
              <th align="right">Rank</th>
              <th colSpan={2} align="right">
                Kills
              </th>
            </tr>
            {Object.keys(constants.BOSSES).map((boss) => {
              if (!data.player[boss + "Rank"]) return;
              return (
                <tr key={boss}>
                  <td align="right">
                    <Image
                      className={styles.miniimg}
                      height={"16px"}
                      width={"16px"}
                      src={`/img/game_icon_${constants.BOSSES[boss]
                        .toLowerCase()
                        .replace(/ /g, "")
                        .replace(/'/g, "")
                        .replace(/\(/g, "")
                        .replace(/\)/g, "")
                        .replace(/-/g, "")}.png`}
                      alt=""
                    />
                  </td>
                  <td>
                    <Link
                      href={`/boss/${boss}?page=${utils.getPageForRank(
                        data.player[boss + "Rank"]
                      )}&gameMode=${
                        data.player.gameMode
                      }&player=${data.player.username.replace(/ /g, "_")}`}
                    >
                      <a>{constants.BOSSES[boss]}</a>
                    </Link>
                  </td>
                  <td align="right">
                    {utils.numberWithCommas(data.player[boss + "Rank"])}
                  </td>
                  <td colSpan={2} align="right">
                    {utils.numberWithCommas(data.player[boss])}
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default PersonalRankingTable;
