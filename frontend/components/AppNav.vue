<template>
  <nav class="menu_wrapper">
    <ul>
      <li>
        <nuxt-link exact to="/">
          HOME
        </nuxt-link>
      </li>
      <li>
        <nuxt-link to="/shop">
          SHOP
        </nuxt-link>
      </li>
      <li>
        <nuxt-link to="/freebies">
          FREEBIES
        </nuxt-link>
      </li>
      <li>
        <nuxt-link to="/aboutme">
          ABOUT ME
        </nuxt-link>
      </li>
      <li>
        <nuxt-link to="/contact">
          CONTACT
        </nuxt-link>
      </li>
    </ul>
  </nav>
</template>

<style>
.menu_wrapper {
    width: 100%;
    text-align: center;
    clear: both;
    line-height: 0;
    height: 70px;
    background-color: rgba(163, 153, 178, 0.34);
    margin-top: 50px;
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
    padding: 0 125px;
    font-family: Audrey;
    font-size: 29px;
    line-height: 60px;
    white-space: nowrap;
    height: 70px;
    display: table-cell;
    vertical-align: middle;
}
.menu_wrapper ul li a:hover {
    text-decoration: none;
}
</style>
