import constants from "../constants";
import styles from "../styles/HighScores.module.css"
import Link from "next/link";

const CategoryMenu = ({data}) => {
  return (
    <nav className={styles["personal-hiscores__side-banner"]}>
      <div className={styles.col1}>
        <div className={styles.headerCategory} />
        {/* <a
            className={`${styles["personal-hiscores__scroll-arrow"]} ${styles["personal-hiscores__scroll-arrow--up"]} ${styles["personal-hiscores__scroll-arrow--disabled"]}`}
            href="#"
            data-js-scroll="up"
          >
            Up
          </a> */}
        <div className={styles.contentCategory}>
          <Link href={`/overall?gameMode=${data.gameMode}`}>
            <a className={styles.Overall}>Overall</a>
          </Link>
          {constants.SKILLS.map((skill) => {
            const skstr = skill.charAt(0).toUpperCase() + skill.slice(1);
            return (
              <Link
                href={`/overall/${skill}?gameMode=${data.gameMode}`}
                key={skill}
              >
                <a className={styles[skstr]}>{skstr}</a>
              </Link>
            );
          })}
          <span
            style={{
              color: "#d9c27e",
              display: "block",
              textAlign: "center",
            }}
          >
            ----
          </span>
          {Object.keys(constants.STATS).map((stat) => {
            return (
              <a
                href={`/stat/${stat}?gameMode=${data.gameMode}`}
                key={stat}
                className={`${styles["activity-link"]} ${styles[stat]}`}
              >
                {constants.STATS[stat]}
              </a>
            );
          })}
          <span
            style={{
              color: "#d9c27e",
              display: "block",
              textAlign: "center",
            }}
          >
            ----
          </span>
          {Object.keys(constants.BOSSES).map((boss) => {
            return (
              <a
                href={`/boss/${boss}?gameMode=${data.gameMode}`}
                key={boss}
                className={`${styles["activity-link"]} ${styles[boss]}`}
              >
                {constants.BOSSES[boss]}
              </a>
            );
          })}
        </div>
        {/* <a
            className={`${styles["personal-hiscores__scroll-arrow"]} ${styles["personal-hiscores__scroll-arrow--down"]}`}
            href="#"
            data-js-scroll="down"
          >
            Down
          </a> */}
        <div className={styles.footerCategory} />
      </div>
    </nav>
  );
};

export default CategoryMenu;
