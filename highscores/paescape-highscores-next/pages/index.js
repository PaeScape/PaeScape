import React, { useEffect } from "react";
import Router from "next/router";

const Comp = () => {
  //...
  useEffect(() => {
    const { pathname } = Router;
    if (pathname === "/") {
      Router.push("/overall");
    }
  });
  //...
};

export default Comp;