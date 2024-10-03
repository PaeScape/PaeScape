import { useRouter } from "next/router";
import { useEffect } from "react";

const FourZeroFour = () => {
  const router = useRouter();

  useEffect(() => {
    router.replace("/overall");
  });

  return null;
};

export default FourZeroFour;
