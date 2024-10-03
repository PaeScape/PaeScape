import BossRankingTable from "../../components/BossRankingTable";
import HighScoresLayout from "../../components/HighScoresLayout";

export async function getServerSideProps(context) {
  let page = 1;
  let gameMode = "normal";
  if (context.query.page) page = context.query.page;
  if (context.query.gameMode) gameMode = context.query.gameMode.toLowerCase();

  // Fetch data from external API
  const res = await fetch(
    `${process.env.API_HOST}/api/v1/boss/${gameMode}/${context.query.boss}/${page}`
  );
  const data = await res.json();

  if (context.query.player) {
    data.highlight = context.query.player.toLowerCase().replace(/_/g, ' ');
  }

  data.gameMode = gameMode;

  // Pass data to the page via props
  return { props: { data } };
}

const BossPage = (props) => {
  return (
    <HighScoresLayout data={props.data}>
      <BossRankingTable data={props.data} />
    </HighScoresLayout>
  );
};

export default BossPage;