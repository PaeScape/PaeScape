import HighScoresLayout from "../../components/HighScoresLayout";
import PersonalRankingTable from "../../components/PersonalRankingTable";

export async function getServerSideProps(context) {
  // Fetch data from external API
  const gameMode = context.query.gameMode ? context.query.gameMode : "normal";
  const res = await fetch(
    `${process.env.API_HOST}/api/v1/personal/${gameMode}/${context.params.username}`
  );
  const data = await res.json();

  if (data.player !== undefined) {
    data.gameMode = data.player.gameMode.toLowerCase();
  } else {
    data.gameMode = gameMode;
    data.notFound = context.params.username;
  }

  // Pass data to the page via props
  return { props: { data } };
}

const PersonalPage = (props) => {
  return (
    <HighScoresLayout data={props.data}>
      <PersonalRankingTable data={props.data} />
    </HighScoresLayout>
  );
};

export default PersonalPage;