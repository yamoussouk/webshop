<template>
  <nav class="menu_wrapper">
    <div class="inner">
      <ul>
        <li>
          <nuxt-link exact to="/">
            <span>HOME</span>
          </nuxt-link>
        </li>
        <li>
          <nuxt-link to="/shop">
            <span>SHOP</span>
          </nuxt-link>
        </li>
        <li>
          <nuxt-link to="/freebies">
            <span>FREEBIES</span>
          </nuxt-link>
        </li>
        <li>
          <nuxt-link to="/aboutme">
            <span>ABOUT ME</span>
          </nuxt-link>
        </li>
        <li>
          <nuxt-link to="/contact">
            <span>CONTACT</span>
          </nuxt-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<style>
.menu_wrapper {
    width: 100%;
    text-align: center;
    clear: both;
    line-height: 0;
    height: 70px;
    margin-top: 50px;
    background-color: rgba(228,214,206,0.88);
}
.menu_wrapper div.inner {
    width: 70%;
    margin-left: 15%;
}
.menu_wrapper ul {
    list-style: none outside;
    height: 70px;
    margin-bottom: 0;
}
.menu_wrapper ul li {
    float: left;
    position: relative;
    display: block;
    height: 70px;
    display: table;
}
.menu_wrapper ul li a {
    color: #000;
    padding: 0 55px;
    font-family: Daun;
    height: 70px;
    display: table-cell;
    vertical-align: middle;
}
.menu_wrapper ul li a span {
    position: relative;
    top: 8px;
    font-size: 40px;
    letter-spacing: 6px;
}
.menu_wrapper ul li a:hover {
    text-decoration: none;
}
li a.nuxt-link-active {
    background-color: rgba(255,255,255,0.4);
}
</style>
