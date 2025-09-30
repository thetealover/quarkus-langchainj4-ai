package com.thetealover.mcp.ws;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class McpWs {
  public static void main(String[] args) {
    Quarkus.run(args);
  }
}
