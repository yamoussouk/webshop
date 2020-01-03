import axios from 'axios'

export default ({ req, store }) => {
  let ip = ''
  let country = ''
  let continent = ''
  // if (process.server) {
  //   ip = req.connection.remoteAddress || req.socket.remoteAddress
  //   store.commit('setIP', ip, undefined, undefined)
  //   console.log('proc_server', store.getters.ip)
  // } else {
  //   ip = store.getters.ip[0]
  // }
  ip = '109.74.53.10'
  store.commit('setIP', ip, undefined, undefined)
  const res = axios.get('http://www.geoplugin.net/json.gp?ip=' + ip
  )
    .then(res => res.data)
    .catch(err => console.error(err))
  res.then((data) => {
    console.log(data)
    country = data.geoplugin_countryCode
    continent = data.geoplugin_continentCode
    store.commit('setIP', ip, country, continent)
    console.log(country)
  })
}
