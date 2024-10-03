import Head from "next/head";
import globalMeta from "../globalmeta";

const SEOHead = () => {
  return (
    <Head>
      <meta
        httpEquiv="content-type"
        content="text/html; charset=windows-1252"
      />
      <title>{`${globalMeta.siteName} Hiscores`}</title>
      <meta name="keywords" content={globalMeta.keywords} />
      <meta name="description" content={globalMeta.description} />
      <link
        rel="icon"
        type="image/vnd.microsoft.icon"
        href="/favicon.ico"
      />
      <link rel="SHORTCUT ICON" href="/favicon.ico" /> {/* TODO */}
      <link rel="apple-touch-icon" href="/logo.png" />{/* TODO */}
      <meta property="og:title" content={`${globalMeta.siteName} Hiscores`} />
      <meta property="og:type" content="website" />
      <meta property="og:site_name" content={globalMeta.siteName} />
      <meta property="og:image" content={globalMeta.siteLogo} />
      <meta property="og:url" content={globalMeta.siteUrl} />
      <meta property="og:description" content={globalMeta.description} />
      <meta itemProp="name" content={globalMeta.siteName} />
      <meta itemProp="description" content={globalMeta.description} />
      <meta name="twitter:card" content="summary_large_image" />
    </Head>
  );
};

export default SEOHead;