import SkillRankingTable from "../../components/SkillRankingTable";
import HighScoresLayout from "../../components/HighScoresLayout";

export async function getServerSideProps(context) {
  let page = 1;
  let gameMode = "normal";
  if (context.query.page) page = context.query.page;
  if (context.query.gameMode) gameMode = context.query.gameMode.toLowerCase();

  // Fetch data from external API
  const res = await fetch(
    `${process.env.API_HOST}/api/v1/overall/${gameMode}/${page}`
  );
  const data = await res.json();

  if (context.query.player) {
    data.highlight = context.query.player.toLowerCase().replace(/_/g, " ");
  }

  data.skill = "overall";
  data.gameMode = gameMode;

  // Pass data to the page via props
  return { props: { data } };
}

const OverallPage = (props) => {
  return (
    <HighScoresLayout data={props.data}>
      <SkillRankingTable data={props.data} />
    </HighScoresLayout>
  );
};

export default OverallPage;
