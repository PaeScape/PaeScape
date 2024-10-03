import globalMeta from "../globalmeta";
import styles from "../styles/HighScores.module.css";
import Image from "next/image";

// TODO
const Footer = () => {
  return (
    <div className="tandc">
      <p className="tandc__copy">
        <a href={globalMeta.discord}>
          <Image
            className={styles["footer__company-img"]}
            src="/img/logo.png"
            alt="company Software"
            height={48}
            width={48}
          />
        </a>
        <br />
        {/* TODO: FOOTER COPY <br /> */}
        <a href={globalMeta.discord}>Discord</a> 
        {/* | <a href="#">FOOTER LINK2</a> */}
      </p>
    </div>
  );
};

export default Footer;
