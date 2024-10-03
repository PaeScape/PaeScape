/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  async redirects() {
    return [
      {
        source: '/',
        destination: '/overall',
        permanent: true,
      },
      {
        source: '/personal',
        destination: '/overall',
        permanent: true,
      },
    ]
  },
};

module.exports = nextConfig;
