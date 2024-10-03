import SEOHead from "./SEOHead";
import styles from "../styles/HighScores.module.css";
import globalMeta from "../globalmeta";
import constants from "../constants";
import Footer from "./Footer";
import utils from "../utils";
import Link from "next/link";
import { useRouter } from "next/router";
import { useState } from "react";
import CategoryMenu from "./CategoryMenu";

const preventDefault = (f) => (e) => {
  e.preventDefault();
  f(e);
};

function HighScoresLayout({ children, data }) {
  const router = useRouter();
  const [query, setQuery] = useState("");

  const handleParam = (setValue) => (e) => setValue(e.target.value);

  const searchByNameSubmit = preventDefault(() => {
    router.push({
      pathname: `/personal/${query.toLowerCase().replace(/ /g, "_")}`,
      query: { gameMode: data.gameMode },
    });
  });
  let path = router.asPath.split('?')[0];
  if(path.indexOf('/personal') !== -1) {
    path = '/overall';
  }
  
  console.log(path);
  const nav = data.player === undefined;
  return (
    <>
      <SEOHead />
      <div className={"centerDiv"}>
        <div className={"frame wide_e"}>
          <span style={{ float: "right" }}>
            <a id="loginLink" href="#">
              Log in
            </a>
          </span>
        </div>
        <br />
        <div className={styles.hiscoretitlebground}>
          <div className={styles.hiscoretitleframe}>
            <strong>
              {globalMeta.siteName}
              {data.gameMode === "normal"
                ? ""
                : ` ${utils.capitalizeEveryWord(
                    data.gameMode.replace(/_/g, " ")
                  )}`}{" "}
              Hiscores
            </strong>
            <br />
            <Link href="/overall">
              <a>Home</a>
            </Link>
          </div>
        </div>
        <br />
        <div align="center">
          <div className={styles["ironman-nav"]}>
            <Link href={path}>
              <a
                className={`${styles["ironman-nav__option"]} ${
                  data.gameMode === "normal"
                    ? styles["ironman-nav__option--current"]
                    : ""
                }`}
              >
                Hiscores
              </a>
            </Link>
            <div className={`${styles["ironman-nav__group"]} `}>
              <a
                className={`${styles["ironman-nav__option"]} ${
                  styles["ironman-nav__option--parent"]
                } ${
                  data.gameMode === "ironman" ||
                  data.gameMode === "ultimate_ironman" ||
                  data.gameMode === "hardcore_ironman"
                    ? styles["ironman-nav__option--current"]
                    : ""
                }`}
              >
                Ironman
              </a>
              <div className={styles["ironman-nav__submenu"]}>
                <Link href={`${path}?gameMode=ironman`}>
                  <a
                    className={`${styles["ironman-nav__option"]} ${
                      data.gameMode === "ironman"
                        ? styles["ironman-nav__option--current"]
                        : ""
                    }`}
                  >
                    Ironman
                  </a>
                </Link>
                <Link href={`${path}?gameMode=ultimate_ironman`}>
                  <a
                    className={`${styles["ironman-nav__option"]} ${
                      data.gameMode === "ultimate_ironman"
                        ? styles["ironman-nav__option--current"]
                        : ""
                    }`}
                  >
                    Ultimate Ironman
                  </a>
                </Link>
                <Link href={`${path}?gameMode=hardcore_ironman`}>
                  <a
                    className={`${styles["ironman-nav__option"]} ${
                      data.gameMode === "hardcore_ironman"
                        ? styles["ironman-nav__option--current"]
                        : ""
                    }`}
                  >
                    Hardcore Ironman
                  </a>
                </Link>
              </div>
            </div>
            {/* <div className={styles["ironman-nav__group"]}>
              <a
                className={`${styles["ironman-nav__option"]} ${styles["ironman-nav__option--parent"]}`}
              >
                Seasonal
              </a>
              <div className={styles["ironman-nav__submenu"]}>
                <a
                  className={styles["ironman-nav__option"]}
                  href="https://secure.runescape.com/m=hiscore_oldschool_deadman/"
                >
                  Deadman Mode
                </a>
                <a
                  className={styles["ironman-nav__option"]}
                  href="https://secure.runescape.com/m=hiscore_oldschool_seasonal/"
                >
                  Leagues
                </a>
                <a
                  className={styles["ironman-nav__option"]}
                  href="https://secure.runescape.com/m=hiscore_oldschool_tournament/"
                >
                  Tournament
                </a>
              </div>
            </div> */}
            {/* <div className={styles["ironman-nav__group"]}>
              <a
                className={`${styles["ironman-nav__option"]} ${styles["ironman-nav__option--parent"]}`}
              >
                Group Ironman
              </a>
              <div className={styles["ironman-nav__submenu"]}>
                <a
                  className={styles["ironman-nav__option"]}
                  href="https://secure.runescape.com/m=hiscore_oldschool_ironman/group-ironman/"
                >
                  Group Ironman
                </a>
                <a
                  className={styles["ironman-nav__option"]}
                  href="https://secure.runescape.com/m=hiscore_oldschool_hardcore_ironman/group-ironman/"
                >
                  Hardcore Group Ironman
                </a>
              </div>
            </div> */}
          </div>
          <div className={styles["personal-hiscores"]}>
            {nav && <CategoryMenu data={data} />}
            <div className={styles["personal-hiscores__table"]}>
              <div className={styles.col2}>
                <div className={styles.headerHiscores} />
                <div className={styles.hiscoresHiddenBG}>{children}</div>
                <div className={styles.footerHiscores} />
              </div>
            </div>
            <div className={styles["personal-hiscores__side-input"]}>
              <form onSubmit={searchByNameSubmit}>
                <div className={styles.smallBox}>
                  <p>
                    <b>Search by name</b>
                    <input
                      id="searchByNameText"
                      className={styles.text}
                      maxLength={12}
                      type="text"
                      value={query}
                      onChange={handleParam(setQuery)}
                      name="user1"
                    />
                    <input
                      maxLength={12}
                      type="submit"
                      name="submit"
                      value="Search"
                    />
                  </p>
                </div>
              </form>
              {/* <form action="overall?category_type=0" method="post">
                  <div className={styles.smallBox}>
                      <p>
                          <b>Search by rank</b>
                          <input className={styles.text} maxlength="12" type="text" name="rank">
                          <input type="hidden" name="table" value="0">
                          <input type="submit" name="submit" value="Search">
                      </p>
                  </div>
              </form> */}
              {/* <form action="https://secure.runescape.com/m=hiscore_oldschool/compare" method="post"
                  className={styles.compareForm}>
                  <div className={styles.largeBox}>
                      <p>
                          <b>Compare Users</b>
                          <input className={styles.text} maxlength="12" type="text" name="user1"
                              pattern="^[a-zA-Z0-9]+([ _-&amp;nbsp;]{0,10}[0-9a-zA-Z]+)*$" title="player name"
                              required="">
                          <input className={styles.text} maxlength="12" type="text" name="user2"
                              pattern="^[a-zA-Z0-9]+([ _-&amp;nbsp;]{0,10}[0-9a-zA-Z]+)*$" title="player name"
                              required="">
                          <input type="submit" name="submit" value="Compare">
                      </p>
                  </div>
              </form> */}
              {/* <div className={styles.largeBox}>
                  <div className={styles.loginPrompt}>
                      <b>Friends Hiscores</b>
                      To view personal<br>
                      hiscores and compare<br>
                      yourself to your friends<br>
                  </div>
                  <a href="https://secure.runescape.com/m=weblogin/oldschool_login?mod=hiscore_oldschool&amp;ssl=0&amp;dest=hiscorefriends_all"
                      className={styles.linkToFriends}>Click here</a>
              </div> */}
            </div>
          </div>
        </div>
        <Footer />
      </div>
    </>
  );
}

export default HighScoresLayout;
